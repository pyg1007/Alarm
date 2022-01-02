package kr.ryan.weatheralarm.repository

import androidx.annotation.WorkerThread
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.room.AlarmDao
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: InsertRepository
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class InsertRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {

    @WorkerThread
    suspend fun provideInsertAlarm(alarm: Alarm) = alarmDao.insertAlarm(alarm)

    @WorkerThread
    suspend fun provideInsertAlarmDate(alarmDate: AlarmDate) = alarmDao.insertAlarmDate(alarmDate)

}