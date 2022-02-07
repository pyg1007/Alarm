package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: DateUtil
 * Created by pyg10.
 * Created On 2021-12-22.
 * Description:
 */

fun Date.convertDateWithDayToString(): String {
    val simpleDateFormat = SimpleDateFormat("MM월 dd일 (E)", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getMeridiem(): String {
    val simpleDateFormat = SimpleDateFormat("a", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.convertTime(): String {
    val simpleDateFormat = SimpleDateFormat("HH : mm", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.convertRemainTime() : String {
    val simpleDateFormat = SimpleDateFormat("HH시간 mm분이 남았습니다.", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getCurrentYear(): Int {
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentMonth(): Int {
    val simpleDateFormat = SimpleDateFormat("MM", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentDate(): Int {
    val simpleDateFormat = SimpleDateFormat("dd", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentHour(): Int {
    val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentMin(): Int {
    val simpleDateFormat = SimpleDateFormat("mm", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun AlarmWithDate.findFastDate() : AlarmDate?{

    Timber.d("$alarmDate")
    Timber.d("${alarmDate.filter { it.date >= Date() }}")
    Timber.d("${alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }}")
    Timber.d("${alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }?.date}")

    return alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }

}