package kr.ryan.weatheralarm.usecase

import kr.ryan.weatheralarm.data.InternalWeather
import kr.ryan.weatheralarm.repository.DBWeatherRepository
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherInsertUseCase
 * Created by pyg10.
 * Created On 2022-02-13.
 * Description:
 */
class WeatherInsertUseCase @Inject constructor(
    private val dbWeatherRepository: DBWeatherRepository
){
    suspend fun insertWeatherInfo(internalWeather: InternalWeather) = dbWeatherRepository.insertWeatherInfo(internalWeather)
}