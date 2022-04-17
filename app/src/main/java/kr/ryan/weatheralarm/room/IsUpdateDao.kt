package kr.ryan.weatheralarm.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.IsWeatherUpdate

/**
 * WeatherAlarm
 * Class: IsUpdateDao
 * Created by pyg10.
 * Created On 2022-04-14.
 * Description:
 */
@Dao
interface IsUpdateDao {

    @Query("SELECT * FROM isweatherupdate")
    fun selectIsWeatherUpdate() : Flow<IsWeatherUpdate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIsWeatherUpdate(isWeatherUpdate: IsWeatherUpdate)

    @Update
    suspend fun updateIsWeatherUpdate(isWeatherUpdate: IsWeatherUpdate)

}