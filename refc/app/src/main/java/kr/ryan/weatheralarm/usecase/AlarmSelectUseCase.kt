package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.repository.AlarmRepositoryImpl
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmSelectUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmSelectUseCase @Inject constructor(
    private val alarmRepositoryImpl: AlarmRepositoryImpl
) {

    suspend fun selectAlarmList() = alarmRepositoryImpl.getAllAlarmList()

}