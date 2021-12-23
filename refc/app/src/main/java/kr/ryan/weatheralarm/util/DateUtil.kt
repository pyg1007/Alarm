package kr.ryan.weatheralarm.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: DateUtil
 * Created by pyg10.
 * Created On 2021-12-22.
 * Description:
 */

fun Date.convertDateString(): String{
    val simpleDateFormat = SimpleDateFormat("MM월 dd일 (E)", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.convertDayString() : String{
    val simpleDateFormat = SimpleDateFormat("E", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getCurrentHour() : Int {
    val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentMin() : Int {
    val simpleDateFormat = SimpleDateFormat("mm", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}