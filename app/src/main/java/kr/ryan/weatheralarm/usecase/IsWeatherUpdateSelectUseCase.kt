package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.repository.IsWeatherUpdateRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateSelectUseCase
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class IsWeatherUpdateSelectUseCase @Inject constructor(
    private val repository: IsWeatherUpdateRepository
) {

    fun selectIsUpdateWeather() = repository.selectIsWeatherUpdate()

}