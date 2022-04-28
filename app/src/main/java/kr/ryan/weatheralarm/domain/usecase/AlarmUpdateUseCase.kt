package kr.ryan.weatheralarm.domain.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.repository.AlarmRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmUpdateUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmUpdateUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke(mode: String, alarm: Alarm?, alarmDate: List<AlarmDate>?){
        when(mode){
            "UpdateAlarm" -> alarmRepository.updateAlarmInfo(alarm!!)
            "UpdateAlarmDate" -> alarmRepository.updateAlarmDate(alarmDate!!)
        }
    }

}