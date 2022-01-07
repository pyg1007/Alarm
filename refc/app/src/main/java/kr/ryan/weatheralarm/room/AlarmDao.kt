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
    @Update
    suspend fun updateAlarmInfo(alarm: Alarm)

    @Delete
    suspend fun deleteAlarmInfo(alarm: Alarm)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarmInfo(alarm: Alarm) : Long

    /**
     *
     * AlarmDate Dao
     *
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarmDate(alarmDate: List<AlarmDate>)

    @Update
    suspend fun updateAlarmDate(alarmDate: List<AlarmDate>)

    @Delete
    suspend fun deleteAlarmDate(alarmDate: List<AlarmDate>)

    /**
     *
     * Alarm Alarm Date Relation
     *
     */

    @Transaction
    @Query("Select * from alarm")
    fun getAllAlarmList() : Flow<List<AlarmWithDate>>

    @Transaction
    suspend fun insertAlarm(alarm: Alarm, alarmDate: List<AlarmDate>){
        val id = insertAlarmInfo(alarm)

        alarmDate.forEach { it.alarmId = id }
        insertAlarmDate(alarmDate)
    }

}