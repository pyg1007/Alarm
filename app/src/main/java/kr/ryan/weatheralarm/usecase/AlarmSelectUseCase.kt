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

    suspend fun selectAlarmWithDate(index: Long) = alarmRepository.selectAlarmWithDate(index)

    suspend fun selectAlarmInfo(alarmIndex: Long) = alarmRepository.selectAlarmInfo(alarmIndex)

    suspend fun selectAlarmDate(index: Long) = alarmRepository.selectAlarmDate(index)

    

    fun selectAlarmList() = alarmRepository.selectAllAlarmList()

}