package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.InternalWeather
import kr.ryan.weatheralarm.room.WeatherDao

/**
 * WeatherAlarm
 * Class: DBWeatherRepositoryImpl
 * Created by pyg10.
 * Created On 2022-02-12.
 * Description:
 */
class DBWeatherRepositoryImpl(
    private val dao: WeatherDao
) : DBWeatherRepository{

    override fun selectWeatherInfo(): Flow<InternalWeather?> {
        return dao.selectWeatherInfo()
    }

    override suspend fun insertWeatherInfo(internalWeather: InternalWeather) {
        dao.insertWeatherInfo(internalWeather)
    }
}