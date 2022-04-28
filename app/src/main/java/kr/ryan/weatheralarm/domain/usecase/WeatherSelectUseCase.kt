package kr.ryan.weatheralarm.domain.usecase

import kr.ryan.weatheralarm.repository.DBWeatherRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherSelectUseCase
 * Created by pyg10.
 * Created On 2022-02-16.
 * Description:
 */
class WeatherSelectUseCase @Inject constructor(
    private val repository: DBWeatherRepository
) {

    fun selectWeatherInfo() = repository.selectWeatherInfo()

}