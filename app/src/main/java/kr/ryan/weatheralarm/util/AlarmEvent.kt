package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: AlarmEvent
 * Created by pyg10.
 * Created On 2022-01-05.
 * Description:
 */
sealed class AlarmEvent{

    data class OnUndoDeleteClick(val alarmWithDate: AlarmWithDate): AlarmEvent()

    data class OnAlarmClick(val alarmWithDate: AlarmWithDate) : AlarmEvent()

    data class OnAddClick(val alarmWithDate: AlarmWithDate): AlarmEvent()

    data class OnDeleteClick(val alarmWithDate: AlarmWithDate): AlarmEvent()

    data class OnAllDeleteClick(val alarmWithDate: List<AlarmWithDate>): AlarmEvent()

}
