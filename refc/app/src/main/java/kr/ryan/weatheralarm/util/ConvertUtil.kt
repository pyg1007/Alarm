package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmStatus

/**
 * WeatherAlarm
 * Class: ConvertUtil
 * Created by pyg10.
 * Created On 2021-12-06.
 * Description:
 */


//fun AlarmStatus.convertAlarm(): Alarm {
//    return when (this) {
//        is AlarmStatus.DaysAlarm -> Alarm(index, title, date, status)
//        is AlarmStatus.DateAlarm -> Alarm(index, title, listOf(date), status)
//        else -> throw IllegalStateException("unKnown Data Type")
//    }
//}