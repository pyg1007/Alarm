package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.CheckWeatherUpdated

/**
 * WeatherAlarm
 * Class: IsWeatherUpdate
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
interface CheckWeatherUpdateRepository {

    fun selectCheckUpdatedWeather() : Flow<CheckWeatherUpdated>

    suspend fun updateWeather(checkWeatherUpdated: CheckWeatherUpdated)

}