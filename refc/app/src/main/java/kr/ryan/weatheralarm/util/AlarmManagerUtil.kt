package kr.ryan.weatheralarm.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kr.ryan.weatheralarm.receiver.AlarmReceiver

/**
 * WeatherAlarm
 * Class: AlarmManagerUtil
 * Created by pyg10.
 * Created On 2022-01-21.
 * Description:
 */

fun AlarmManager.registerAlarm() {

}

fun Context.isRegisterAlarm(alarmId: Int): Boolean {
    val intent = Intent(this, AlarmReceiver::class.java)
    val sender = PendingIntent.getBroadcast(
        this, alarmId, intent,
        if (Build.VERSION.SDK_INT >= 30)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        else
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    return sender != null
}

fun AlarmManager.cancelAlarm(context: Context, alarmId: Int) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val sender =
        PendingIntent.getBroadcast(
            context, alarmId, intent,
            if (Build.VERSION.SDK_INT >= 30)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    sender?.let {
        cancel(it)
        it.cancel()
    }
}