package kr.ryan.weatheralarm.data

import java.util.*

/**
 * WeatherAlarm
 * Class: AlarmStatus
 * Created by pyg10.
 * Created On 2021-11-12.
 * Description:
 */
sealed class AlarmStatus{

    data class DateAlarm(val index: Long, var title: String?, val date: Date, var status: Boolean) : AlarmStatus()
    data class DaysAlarm(val index: Long, var title: String?, val date: List<Date>, var status: Boolean) : AlarmStatus()

}
