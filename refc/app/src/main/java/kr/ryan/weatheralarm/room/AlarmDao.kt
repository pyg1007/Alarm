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

    /**
     *
     * AlarmDate Dao
     *
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarmDate(vararg alarmDate: AlarmDate)

    @Update
    suspend fun updateAlarmDate(vararg alarmDate: AlarmDate)

    @Delete
    suspend fun deleteAlarmDate(vararg alarmDate: AlarmDate)

    /**
     *
     * Alarm Alarm Date Relation
     *
     */

    @Transaction
    @Query("Select * from alarm")
    fun getAllAlarmList() : Flow<List<AlarmWithDate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarmWithDate: AlarmWithDate)

    @Delete
    suspend fun deleteAlarm(alarmWithDate: AlarmWithDate)

    @Update
    suspend fun updateAlarm(alarmWithDate: AlarmWithDate)

}