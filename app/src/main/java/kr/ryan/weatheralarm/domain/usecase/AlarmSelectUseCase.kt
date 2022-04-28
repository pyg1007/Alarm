package kr.ryan.weatheralarm.domain.usecase

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

    @Suppress("UNCHECKED_CAST")
    suspend operator fun<T> invoke(mode: String, index: Long?, alarmIndex: Long?): T{
        return when(mode){
            "Select" -> alarmRepository.selectAllAlarmList() as T
            "SelectAlarmInfo" -> alarmRepository.selectAlarmInfo(alarmIndex!!) as T
            "SelectAlarmDate" -> alarmRepository.selectAlarmDate(index!!) as T
            "SelectAlarmWithDate" -> alarmRepository.selectAlarmWithDate(index!!) as T
            else -> throw Exception("")
        }
    }

}