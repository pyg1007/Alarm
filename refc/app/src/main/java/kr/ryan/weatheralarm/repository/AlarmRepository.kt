package kr.ryan.weatheralarm.repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: AlarmRepository
 * Created by pyg10.
 * Created On 2022-01-05.
 * Description:
 */
interface AlarmRepository {

    /**
     *
     * Alarm Dao
     *
     */
    suspend fun updateAlarmInfo(alarm: Alarm)

    /**
     *
     * AlarmDate Dao
     *
     */
    suspend fun insertAlarmDate(vararg alarmDate: AlarmDate)

    suspend fun updateAlarmDate(vararg alarmDate: AlarmDate)

    suspend fun deleteAlarmDate(vararg alarmDate: AlarmDate)

    /**
     *
     * Alarm Alarm Date Relation
     *
     */

    fun getAllAlarmList() : Flow<List<AlarmWithDate>>

    suspend fun insertAlarm(alarmWithDate: AlarmWithDate)

    suspend fun deleteAlarm(alarmWithDate: AlarmWithDate)

    suspend fun updateAlarm(alarmWithDate: AlarmWithDate)

}