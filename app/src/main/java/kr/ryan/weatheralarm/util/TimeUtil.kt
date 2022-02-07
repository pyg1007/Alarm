package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.AlarmWithDate
import java.util.*

/**
 * WeatherAlarm
 * Class: TimeUtil
 * Created by pyg10.
 * Created On 2022-02-07.
 * Description:
 */

fun List<AlarmWithDate>?.findFastAlarmDate() : String? {
    val alarmOnList = this?.filter{ it.alarm.onOff }

    val filteredDateAlarm = alarmOnList?.filter { !it.alarm.isRepeat }
    val filteredDaysAlarm = alarmOnList?.filter { it.alarm.isRepeat }

    val sortedDateAlarm = filteredDateAlarm?.sortedBy { it.alarmDate[0].date }
    val sortedDaysAlarm = filteredDaysAlarm?.sortedBy { it.alarmDate.sortedBy { alarmDate ->  alarmDate.date }[0].date }

    val fastDate = if (!sortedDateAlarm.isNullOrEmpty())
        sortedDateAlarm[0].alarmDate[0].date
    else
        null

    val fastDays = if (!sortedDaysAlarm.isNullOrEmpty())
        sortedDaysAlarm[0].alarmDate[0].date
    else
        null

    return if (null == fastDate && null != fastDays){
        fastDays.convertTime()
    }else if (null != fastDate && null == fastDays){
        fastDate.convertTime()
    }else if (null != fastDate && null != fastDays){
        if (fastDate.after(fastDays))
            fastDays.convertTime()
        else
            fastDate.convertTime()
    }else{
        null
    }
}