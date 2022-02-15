package kr.ryan.weatheralarm.di

import android.app.Application
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.util.getCertainTime
import kr.ryan.weatheralarm.worker.WeatherWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmApplication
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
@HiltAndroidApp
class AlarmApplication : Application(){

    companion object{
        private const val WORKER_UNIQUE_ID = "Weather"
    }

    private val backgroundCoroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

//        backgroundCoroutine.launch {
//            createWorkManager()
//        }

        Timber.plant(Timber.DebugTree())
    }

    private fun createWorkManager(){

        // 네트워크에 연결되어있고 기기가 유휴상태인경우
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        // 새벽 두시마다 등록
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WeatherWorker>()
            .setConstraints(constraint)
            .setInitialDelay(getCertainTime(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(WORKER_UNIQUE_ID, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)
    }


}