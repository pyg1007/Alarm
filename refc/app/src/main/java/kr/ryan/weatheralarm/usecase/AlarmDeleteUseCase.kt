package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.repository.DeleteRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmDeleteUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmDeleteUseCase @Inject constructor(
    private val deleteRepository: DeleteRepository
) {

    suspend fun deleteAlarm(alarm: Alarm) : Long = deleteRepository.provideDeleteAlarm(alarm)

}