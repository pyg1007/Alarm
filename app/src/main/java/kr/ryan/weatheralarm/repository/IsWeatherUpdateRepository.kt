package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.IsWeatherUpdate

/**
 * WeatherAlarm
 * Class: IsWeatherUpdate
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
interface IsWeatherUpdateRepository {

    fun selectIsWeatherUpdate() : Flow<IsWeatherUpdate>

    suspend fun updateIsWeatherUpdate(isWeatherUpdate: IsWeatherUpdate)

}