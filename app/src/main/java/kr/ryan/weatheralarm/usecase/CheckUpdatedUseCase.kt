package kr.ryan.weatheralarm.usecase

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
class CheckUpdatedUseCase @Inject constructor(
    private val repository: CheckWeatherUpdateRepository
) {

    suspend fun isUpdateWeather(checkWeatherUpdated: CheckWeatherUpdated) = repository.updateIsWeatherUpdate(checkWeatherUpdated)

}