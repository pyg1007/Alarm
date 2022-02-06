package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.repository.AlarmRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmInsertUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmInsertUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend fun insertAlarm(alarm: Alarm, alarmDate: List<AlarmDate>) =
        alarmRepository.insertAlarm(alarm, alarmDate)


    suspend fun insertAlarmDate(alarmDate: List<AlarmDate>) =
        alarmRepository.insertAlarmDate(alarmDate)

}