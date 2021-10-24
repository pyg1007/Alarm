package kr.ryan.weatheralarm.repository

import androidx.annotation.WorkerThread
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.room.AlarmDao
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: DeleteRepository
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
class DeleteRepository @Inject constructor(
    private val alarmDao: AlarmDao
) {

    @WorkerThread
    suspend fun provideDeleteAlarm(alarm: Alarm): Long = alarmDao.deleteAlarm(alarm)

}