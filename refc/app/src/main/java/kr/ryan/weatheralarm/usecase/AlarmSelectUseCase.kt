package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.repository.AlarmRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmSelectUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmSelectUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend fun selectAlarmInfo(alarmIndex: Int) = alarmRepository.selectAlarmInfo(alarmIndex)

    fun selectAlarmList() = alarmRepository.getAllAlarmList()

}