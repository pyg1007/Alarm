package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.repository.UpdateRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmUpdateUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmUpdateUseCase @Inject constructor(
    private val updateRepository: UpdateRepository
) {

    suspend fun updateAlarm(alarm: Alarm): Long = updateRepository.provideUpdateAlarm(alarm)

}