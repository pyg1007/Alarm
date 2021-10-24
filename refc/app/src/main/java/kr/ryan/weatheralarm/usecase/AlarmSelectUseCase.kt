package kr.ryan.weatheralarm.usecase

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.repository.SelectRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmSelectUseCase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class AlarmSelectUseCase @Inject constructor(
    private val selectRepository: SelectRepository
) {

    suspend fun selectAlarm(): Flow<List<Alarm>> = selectRepository.provideAlarmList()

}