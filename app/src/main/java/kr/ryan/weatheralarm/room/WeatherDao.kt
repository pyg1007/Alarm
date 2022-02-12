package kr.ryan.weatheralarm.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.InternalWeather

/**
 * WeatherAlarm
 * Class: WeatherDao
 * Created by pyg10.
 * Created On 2022-02-12.
 * Description:
 */
@Dao
interface WeatherDao {

    @Query("SELECT * FROM internalweather")
    fun selectWeatherInfo() : Flow<InternalWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfo(internalWeather: InternalWeather)
}