package kr.ryan.alarm.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.alarm.data.Alarm

/**
 * Alarm
 * Class: AlarmDao
 * Created by pyg10.
 * Created On 2021-08-05.
 * Description:
 */

@Dao
interface AlarmDao {

    @Query("SELECT * FROM Alarm_table")
    fun allAlarm(): Flow<List<Alarm>>

    @Insert
    suspend fun insertAlarm(alarm: Alarm): Long

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

}