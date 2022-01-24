package kr.ryan.weatheralarm.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: AlarmReceiver
 * Created by pyg10.
 * Created On 2022-01-21.
 * Description:
 */
class AlarmReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {

        Timber.d("Alarm Receiver Active")
    }

}