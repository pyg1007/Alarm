package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.repository.AlarmRepositoryImpl
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmDeleteUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmDeleteUseCase @Inject constructor(
    private val alarmRepositoryImpl: AlarmRepositoryImpl
) {

    suspend fun deleteAlarm(alarmWithDate: AlarmWithDate) = alarmRepositoryImpl.deleteAlarm(alarmWithDate)
    
    suspend fun deleteAlarmDate(vararg alarmDate: AlarmDate) = alarmRepositoryImpl.deleteAlarmDate(*alarmDate)

}