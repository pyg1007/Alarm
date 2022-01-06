package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.repository.AlarmRepositoryImpl
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmUpdateUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmUpdateUseCase @Inject constructor(
    private val alarmRepositoryImpl: AlarmRepositoryImpl
) {

    suspend fun updateAlarmInfo(alarm: Alarm) = alarmRepositoryImpl.updateAlarmInfo(alarm)

    suspend fun updateAlarmDate(vararg alarmDate: AlarmDate) = alarmRepositoryImpl.updateAlarmDate(*alarmDate)

    suspend fun updateAlarm(alarmWithDate: AlarmWithDate) = alarmRepositoryImpl.updateAlarm(alarmWithDate)

}