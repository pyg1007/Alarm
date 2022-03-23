package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * WeatherAlarm
 * Class: DateUtil
 * Created by pyg10.
 * Created On 2021-12-22.
 * Description:
 */

private fun Date.dateToString(pattern: String, locale: Locale = Locale.getDefault()): String{
    val simpleDateFormat = SimpleDateFormat(pattern, locale)
    return simpleDateFormat.format(this)
}

fun Date.convertDateWithDayToString(): String = dateToString("MM월 dd일 (E)")

fun Date.getMeridiem(): String = dateToString("a")

fun Date.convertTime(): String = dateToString("HH : mm")

fun Date.convertBaseDate(): String = dateToString("yyyyMMdd")

fun Date.convertBaseTime() : String = dateToString("HHmm")

fun Date.convertDate() : String = dateToString("yyyyMMdd (E) HH:mm")

fun Date.convertRemainTime() : String {
    val calTime = (time - Date().time) / 1000L
    val simpleDateFormat = when{
        (3600 * 24 * 365).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime  ->  SimpleDateFormat("y년이 남았습니다.", Locale.getDefault())
        (3600 * 24 * 100).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime
                && (3600 * 24 * 365).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds > calTime ->  SimpleDateFormat("D일 HH시간 mm분이 남았습니다.", Locale.getDefault())
        (3600 * 24 * 10).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime
                && (3600 * 24 * 100).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds > calTime ->  SimpleDateFormat("dd일 HH시간 mm분이 남았습니다.", Locale.getDefault())
        (3600 * 24).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime
                && (3600 * 24 * 10).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds > calTime-> SimpleDateFormat("d일 HH시간 mm분이 남았습니다.", Locale.getDefault())
        3600.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime
                && (3600 * 24).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds > calTime-> SimpleDateFormat("HH시간 mm분이 남았습니다.", Locale.getDefault())
        60.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds <= calTime
                && 3600.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds > calTime -> SimpleDateFormat("mm분이 남았습니다.", Locale.getDefault())
        else -> SimpleDateFormat("잠시후 알람이 울립니다.", Locale.getDefault())
    }
    return simpleDateFormat.format(calTime*1000)
}

fun Date.getCurrentYear(): Int = dateToString("yyyy").toInt()

fun Date.getCurrentMonth(): Int = dateToString("MM").toInt()

fun Date.getCurrentDate(): Int = dateToString("dd").toInt()

fun Date.getCurrentHour(): Int = dateToString("HH").toInt()

fun Date.getCurrentMin(): Int = dateToString("mm").toInt()

fun AlarmWithDate.findFastDate() : AlarmDate?{

    Timber.d("$alarmDate current Date -> ${Date().convertDateWithDayToString()} ${Date().convertTime()}")
    Timber.d("${alarmDate.filter { it.date >= Date() }}")
    Timber.d("${alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }}")
    Timber.d("${alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }?.date}")

    return alarmDate.filter { it.date >= Date() }.minByOrNull { it.date }

}