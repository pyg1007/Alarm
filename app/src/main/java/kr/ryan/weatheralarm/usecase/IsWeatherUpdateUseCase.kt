package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.IsWeatherUpdate
import kr.ryan.weatheralarm.repository.IsWeatherUpdateRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateUseCase
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class IsWeatherUpdateUseCase @Inject constructor(
    private val repository: IsWeatherUpdateRepository
) {

    suspend fun isUpdateWeather(isWeatherUpdate: IsWeatherUpdate) = repository.updateIsWeatherUpdate(isWeatherUpdate)

}