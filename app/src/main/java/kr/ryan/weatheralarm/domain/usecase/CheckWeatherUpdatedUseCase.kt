package kr.ryan.weatheralarm.domain.usecase

import kr.ryan.weatheralarm.data.CheckWeatherUpdated
import kr.ryan.weatheralarm.repository.CheckWeatherUpdateRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateUseCase
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class CheckWeatherUpdatedUseCase @Inject constructor(
    private val repository: CheckWeatherUpdateRepository
) {

    suspend operator fun invoke(checkWeatherUpdated: CheckWeatherUpdated) = repository.updateWeather(checkWeatherUpdated)

}