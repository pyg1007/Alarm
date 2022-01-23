package kr.ryan.weatheralarm.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * WeatherAlarm
 * Class: AlarmReceiver
 * Created by pyg10.
 * Created On 2022-01-21.
 * Description:
 */
class AlarmReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, p1: Intent?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when{
            Build.VERSION.SDK_INT >= 31 -> {
                when{
                    alarmManager.canScheduleExactAlarms() -> {

                    }
                }
            }
            else -> {

            }
        }
    }

}