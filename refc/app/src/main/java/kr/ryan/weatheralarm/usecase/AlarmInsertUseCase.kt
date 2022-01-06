package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.repository.AlarmRepositoryImpl
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmInsertUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmInsertUseCase @Inject constructor(
    private val alarmRepositoryImpl: AlarmRepositoryImpl
) {

    suspend fun insertAlarm(alarmWithDate: AlarmWithDate) = alarmRepositoryImpl.insertAlarm(alarmWithDate)


    suspend fun insertAlarmDate(vararg alarmDate: AlarmDate) = alarmRepositoryImpl.insertAlarmDate(*alarmDate)

}