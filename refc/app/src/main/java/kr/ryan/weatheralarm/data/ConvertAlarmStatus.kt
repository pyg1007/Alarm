package kr.ryan.weatheralarm.data

/**
 * WeatherAlarm
 * Class: ConvertAlarmStatus
 * Created by pyg10.
 * Created On 2021-11-12.
 * Description:
 */


fun convertAlarmStatus(alarmWithDate: AlarmWithDate): AlarmStatus {
    return if (alarmWithDate.alarmDate.size == 1)
        AlarmStatus.DateAlarm(
            alarmWithDate.alarm.index,
            alarmWithDate.alarm.title,
            alarmWithDate.alarmDate[0].date,
            alarmWithDate.alarm.onOff
        )
    else
        AlarmStatus.DaysAlarm(
            alarmWithDate.alarm.index,
            alarmWithDate.alarm.title,
            alarmWithDate.alarmDate.map {
                it.date
            },
            alarmWithDate.alarm.onOff
        )
}