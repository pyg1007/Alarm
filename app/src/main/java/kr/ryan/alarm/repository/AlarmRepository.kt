package kr.ryan.alarm.repository

import androidx.annotation.WorkerThread
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.room.ApplicationDatabase

/**
 * Alarm
 * Class: AlarmRepository
 * Created by pyg10.
 * Created On 2021-08-06.
 * Description:
 */
class AlarmRepository(private val database: ApplicationDatabase) {

    val alarmList = database.alarmDao().allAlarm()

    @WorkerThread
    suspend fun insertAlarm(alarm: Alarm) {
        database.alarmDao().insertAlarm(alarm)
    }

    @WorkerThread
    suspend fun deleteAlarm(alarm: Alarm) {
        database.alarmDao().deleteAlarm(alarm)
    }


}