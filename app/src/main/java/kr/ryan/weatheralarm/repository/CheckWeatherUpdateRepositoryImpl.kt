package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.CheckWeatherUpdated
import kr.ryan.weatheralarm.room.CheckUpdatedDao

/**
 * WeatherAlarm
 * Class: IsWeatherUpdateRepositoryImpl
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
class CheckWeatherUpdateRepositoryImpl(private val dao: CheckUpdatedDao) : CheckWeatherUpdateRepository{

    override suspend fun updateWeather(checkWeatherUpdated: CheckWeatherUpdated) {
        dao.updateCheckWeatherUpdate(checkWeatherUpdated)
    }

    override fun selectCheckWeatherUpdate(): Flow<CheckWeatherUpdated> {
        return dao.selectCheckWeatherUpdate()
    }

}