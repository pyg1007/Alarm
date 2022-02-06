package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.repository.AlarmRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmDeleteUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmDeleteUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend fun deleteAlarm(alarm: Alarm) = alarmRepository.deleteAlarmInfo(alarm)
    
    suspend fun deleteAlarmDate(alarmDate: List<AlarmDate>) = alarmRepository.deleteAlarmDate(alarmDate)

}