package kr.ryan.weatheralarm.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.CheckWeatherUpdated

/**
 * WeatherAlarm
 * Class: IsUpdateDao
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
@Dao
interface CheckUpdatedDao {

    @Query("SELECT * FROM checkweatherupdated")
    fun selectCheckWeatherUpdate() : Flow<CheckWeatherUpdated>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckWeatherUpdate(checkWeatherUpdated: CheckWeatherUpdated)

    @Update
    suspend fun updateCheckWeatherUpdate(checkWeatherUpdated: CheckWeatherUpdated)

}