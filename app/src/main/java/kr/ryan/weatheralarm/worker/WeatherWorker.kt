package kr.ryan.weatheralarm.worker

import android.content.Context
import android.location.LocationManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * WeatherAlarm
 * Class: WeatherWorker
 * Created by pyg10.
 * Created On 2022-02-14.
 * Description:
 */
class WeatherWorker constructor(
    appContext: Context,
    parameters: WorkerParameters
) :
    CoroutineWorker(appContext, parameters) {

    private var retry = 0

    override suspend fun doWork(): Result {

        runCatching {

            val locationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

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