package kr.ryan.weatheralarm.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.room.AlarmDao
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: SelectRepository
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class SelectRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {
    @WorkerThread
    suspend fun provideAlarmList(): Flow<List<Alarm>> = alarmDao.getAllAlarm()
}