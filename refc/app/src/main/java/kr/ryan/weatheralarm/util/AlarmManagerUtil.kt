package kr.ryan.weatheralarm.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.receiver.AlarmReceiver
import timber.log.Timber
import java.util.*

/**
 * WeatherAlarm
 * Class: AlarmManagerUtil
 * Created by pyg10.
 * Created On 2022-01-21.
 * Description:
 */

fun AlarmManager.registerAlarm(context: Context, alarmWithDate: AlarmWithDate) {
    Timber.d("register ${alarmWithDate.findFastDate()?.date?.convertDateWithDayToString()}")
    val intent = Intent(context, AlarmReceiver::class.java).also {
        if (alarmWithDate.alarm.isRepeat)
            it.putExtra("alarm", alarmWithDate.findFastDate())
        else
            it.putExtra("alarm", alarmWithDate.alarmDate[0])
    }
    val sender = PendingIntent.getBroadcast(
        context, alarmWithDate.alarm.pendingId, intent,
        if (Build.VERSION.SDK_INT >= 30)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        else
            PendingIntent.FLAG_CANCEL_CURRENT
    )
    if (Build.VERSION.SDK_INT >= 31){
        if (!alarmWithDate.alarm.isRepeat) {
            if (canScheduleExactAlarms())
                setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmWithDate.alarmDate[0].date.time,
                    sender
                )
            else
                setExact(AlarmManager.RTC_WAKEUP, alarmWithDate.alarmDate[0].date.time, sender)
        }else{
            if (canScheduleExactAlarms())
                setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmWithDate.findFastDate()?.date?.time!!,
                    sender
                )
            else
                setExact(AlarmManager.RTC_WAKEUP, alarmWithDate.findFastDate()?.date?.time!!, sender)
        }
    }else{
        if (!alarmWithDate.alarm.isRepeat)
            setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmWithDate.alarmDate[0].date.time, sender)
        else
            setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmWithDate.findFastDate()?.date?.time!!, sender)
    }
}

fun Context.isRegisterAlarm(alarmWithDate: AlarmWithDate): Boolean {
    val intent = Intent(this, AlarmReceiver::class.java)
    val sender = PendingIntent.getBroadcast(
        this, alarmWithDate.alarm.pendingId, intent,
        if (Build.VERSION.SDK_INT >= 30)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        else
            PendingIntent.FLAG_UPDATE_CURRENT
    )
    return sender != null
}

fun AlarmManager.cancelAlarm(context: Context, alarmWithDate: AlarmWithDate) {
    val intent = Intent(context, AlarmReceiver::class.java)
    val sender =
        PendingIntent.getBroadcast(
            context, alarmWithDate.alarm.pendingId, intent,
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