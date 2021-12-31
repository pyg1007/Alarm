package kr.ryan.weatheralarm.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: AlarmDao
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
@Dao
interface AlarmDao {
    /**
     *
     * Alarm Dao
     *
     */
    @Query("Select * from Alarm")
    fun getAllAlarm(): Flow<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarm: Alarm): Long

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    /**
     *
     * AlarmDate Dao
     *
     */

    @Query("Select * from date")
    fun getAllAlarmDate() : Flow<List<AlarmDate>>

    @Insert
    suspend fun insertAlarmDate(alarmDate: AlarmDate): Long

    @Update
    suspend fun updateAlarmDate(alarmDate: AlarmDate)

    @Delete
    suspend fun deleteAlarmDate(alarmDate: AlarmDate)

    /**
     *
     * Alarm Alarm Date Relation
     *
     */

    @Transaction
    @Query("Select * from alarm")
    fun getRelation() : Flow<List<AlarmWithDate>>
}