package kr.ryan.weatheralarm.usecase

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

    fun selectCheckWeatherUpdateWeather() = repository.selectCheckWeatherUpdate()

}