package kr.ryan.weatheralarm.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ryan.permissionmodule.requestPermission
import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.BuildConfig
import kr.ryan.weatheralarm.data.Mapping.convertWeatherToInternalWeather
import kr.ryan.weatheralarm.di.AlarmApplication
import kr.ryan.weatheralarm.usecase.WeatherInsertUseCase
import kr.ryan.weatheralarm.usecase.WeatherUseCase
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.TO_GRID
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherWorker
 * Created by pyg10.
 * Created On 2022-02-14.
 * Description:
 */
@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted parameters: WorkerParameters
) :
    CoroutineWorker(appContext, parameters) {

    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Inject
    lateinit var useCase: WeatherUseCase

    @Inject
    lateinit var insertUseCase: WeatherInsertUseCase

    private var retry = 0

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {

        runCatching {

            CoroutineScope(Dispatchers.Default).launch {
                val locationManager =
                    applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                requestPermission({
                    val latXLngY = applicationContext.getCurrentLatXLngY()!!
                    val result = useCase.getWeatherInfo(
                        hashMapOf(
                            "serviceKey" to BuildConfig.weather_api_key,
                            "nx" to latXLngY.x.toString(),
                            "ny" to latXLngY.y.toString(),
                            "dataType" to "JSON",
                            "base_date" to Date().convertBaseDate(),
                            "base_time" to Calendar.getInstance().apply {
                                time = Date()
                                set(Calendar.HOUR_OF_DAY, 2)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                            }.time.convertBaseTime()
                        )
                    )

                    when(result){
                        is NetWorkResult.Success -> {
                            result.data.convertWeatherToInternalWeather()?.let {
                                Timber.d("Worker & NetWork Success")
                                insertUseCase.insertWeatherInfo(it)
                            }
                        }
                        else -> {
                            Timber.d("Error")
                        }
                    }


                }, {
                    Timber.d("denied permissions => $it")
                }, *permission)

            }

            // 네트워크에 연결되어있고 기기가 유휴상태인경우
            val constraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresDeviceIdle(true)
                .build()

            // 새벽 두시 20분마다 등록
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WeatherWorker>()
                .setConstraints(constraint)
                .setInitialDelay(getCertainTime(), TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(applicationContext).enqueueUniqueWork(AlarmApplication.WORKER_UNIQUE_ID, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)



        }.onFailure {

            retry++

            if (retry < 3)
                Result.retry()
            else
                Result.failure()

        }

        return Result.success()
    }
}