package kr.ryan.weatheralarm.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.CheckWeatherUpdated
import kr.ryan.weatheralarm.repository.CheckWeatherUpdateRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateSelectUseCase
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class CheckWeatherUpdatedSelectUseCase @Inject constructor(
    private val repository: CheckWeatherUpdateRepository
) {

    operator fun invoke() : Flow<CheckWeatherUpdated> = repository.selectCheckWeatherUpdate()

}