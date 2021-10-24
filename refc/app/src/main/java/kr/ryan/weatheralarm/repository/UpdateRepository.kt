package kr.ryan.weatheralarm.repository

import androidx.annotation.WorkerThread
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.room.AlarmDao
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: UpdateRepository
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class UpdateRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {

    @WorkerThread
    suspend fun provideUpdateAlarm(alarm: Alarm) : Long = alarmDao.updateAlarm(alarm)

}