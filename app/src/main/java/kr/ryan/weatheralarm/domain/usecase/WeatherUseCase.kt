package kr.ryan.weatheralarm.domain.usecase

import kr.ryan.weatheralarm.repository.WeatherRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherUseCase
 * Created by pyg10.
 * Created On 2022-02-11.
 * Description:
 */
class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend fun getWeatherInfo(params: HashMap<String, String>) = weatherRepository.provideWeatherInfo(params)

}