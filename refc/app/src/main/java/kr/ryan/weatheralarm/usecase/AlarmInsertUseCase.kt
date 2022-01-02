package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.repository.InsertRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmInsertUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmInsertUseCase @Inject constructor(
    private val insertRepository: InsertRepository
) {

    suspend fun insertAlarm(alarm:Alarm) = insertRepository.provideInsertAlarm(alarm)


    suspend fun insertAlarmDate(alarmDate: AlarmDate) = insertRepository.provideInsertAlarmDate(alarmDate)

}