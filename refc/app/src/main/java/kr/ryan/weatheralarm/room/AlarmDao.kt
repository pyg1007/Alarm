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
    @Query("SELECT * FROM alarm WHERE `index` = :alarmIndex")
    suspend fun getAlarmInfo(alarmIndex: Long) : Alarm

    @Update
    suspend fun updateAlarmInfo(alarm: Alarm)

    @Delete
    suspend fun deleteAlarmInfo(alarm: Alarm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmInfo(alarm: Alarm) : Long

    /**
     *
     * AlarmDate Dao
     *
     */

    @Query("SELECT * FROM date WHERE `alarmIndex` = :alarmIndex")
    suspend fun getAlarmDate(alarmIndex: Long) : List<AlarmDate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    @Query("SELECT * FROM alarm WHERE `index` = :index")
    suspend fun getAlarmWithDate(index: Long) : AlarmWithDate

    @Transaction
    @Query("Select * from alarm")
    fun getAllAlarmList() : Flow<List<AlarmWithDate>>

    @Transaction
    suspend fun insertAlarm(alarm: Alarm, alarmDate: List<AlarmDate>){
        val id = insertAlarmInfo(alarm)

        alarmDate.forEach { it.alarmIndex = id }
        insertAlarmDate(alarmDate)
    }

}