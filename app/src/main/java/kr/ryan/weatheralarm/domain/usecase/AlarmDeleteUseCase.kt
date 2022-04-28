package kr.ryan.weatheralarm.domain.usecase

import kr.ryan.weatheralarm.data.Alarm
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

    suspend operator fun invoke(mode: String, vararg alarm: Alarm){
        when(mode){
            "Single" -> alarmRepository.deleteAlarmInfo(alarm[0])
            "Multi" -> alarmRepository.deleteAllAlarm(*alarm)
        }
    }

}