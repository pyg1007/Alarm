package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.IsWeatherUpdate
import kr.ryan.weatheralarm.room.IsUpdateDao

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateRepositoryImpl
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class IsWeatherUpdateRepositoryImpl(private val dao: IsUpdateDao) : IsWeatherUpdateRepository{

    override fun selectIsWeatherUpdate(): Flow<IsWeatherUpdate> {
        return dao.selectIsWeatherUpdate()
    }

    override suspend fun updateIsWeatherUpdate(isWeatherUpdate: IsWeatherUpdate) {
        dao.updateIsWeatherUpdate(isWeatherUpdate)
    }
}