package kr.ryan.weatheralarm.di

import android.app.Application
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: AlarmApplication
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
@HiltAndroidApp
class AlarmApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG)
            Timber.plant(Timber.asTree())
    }
}