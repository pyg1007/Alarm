package kr.ryan.weatheralarm.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm

/**
 * WeatherAlarm
 * Class: AlarmDao
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
@Dao
interface AlarmDao {

    @Query("Select * from Alarm")
    fun getAllAlarm(): Flow<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarm: Alarm): Long

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

}