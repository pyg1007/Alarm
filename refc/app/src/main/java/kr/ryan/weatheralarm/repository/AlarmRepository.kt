package kr.ryan.weatheralarm.repository

import androidx.room.*
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    suspend fun deleteAlarmInfo(alarm: Alarm)

    /**
     *
     * AlarmDate Dao
     *
     */
    suspend fun insertAlarmDate(alarmDate: List<AlarmDate>)

    suspend fun updateAlarmDate(alarmDate: List<AlarmDate>)

    suspend fun deleteAlarmDate(alarmDate: List<AlarmDate>)

    /**
     *
     * Alarm Alarm Date Relation
     *
     */

    fun getAllAlarmList() : Flow<List<AlarmWithDate>>

    suspend fun insertAlarm(alarm: Alarm, alarmDate: List<AlarmDate>)

}