package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.InternalWeather

/**
 * WeatherAlarm
 * Class: WeatherInsertRepository
 * Created by pyg10.
 * Created On 2022-02-12.
 * Description:
 */
interface DBWeatherRepository {

    fun selectWeatherInfo(): Flow<InternalWeather?>

    suspend fun insertWeatherInfo(internalWeather: InternalWeather)
}