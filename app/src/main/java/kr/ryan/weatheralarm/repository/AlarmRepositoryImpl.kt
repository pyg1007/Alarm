package kr.ryan.weatheralarm.repository

import kotlinx.coroutines.flow.Flow
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.room.AlarmDao

/**
 * WeatherAlarm
 * Class: AlarmRepositoryImpl
 * Created by pyg10.
 * Created On 2022-01-05.
 * Description:
 */
class AlarmRepositoryImpl(
    private val dao: AlarmDao
) : AlarmRepository {

    override suspend fun selectAlarmInfo(alarmIndex: Long): Alarm {
        return dao.getAlarmInfo(alarmIndex)
    }

    override suspend fun selectAlarmDate(alarmIndex: Long): List<AlarmDate> {
        return dao.getAlarmDate(alarmIndex)
    }

    override suspend fun deleteAllAlarm(vararg alarm: Alarm) {
        dao.deleteAllAlarm(*alarm)
    }

    override suspend fun updateAlarmInfo(alarm: Alarm) {
        dao.updateAlarmInfo(alarm)
    }

    override suspend fun deleteAlarmInfo(alarm: Alarm) {
        dao.deleteAlarmInfo(alarm)
    }

    override suspend fun insertAlarmDate(alarmDate: List<AlarmDate>) {
        dao.insertAlarmDate(alarmDate)
    }

    override suspend fun updateAlarmDate(alarmDate: List<AlarmDate>) {
        dao.updateAlarmDate(alarmDate)
    }

    override suspend fun deleteAlarmDate(alarmDate: List<AlarmDate>) {
        dao.deleteAlarmDate(alarmDate)
    }

    override suspend fun selectAlarmWithDate(index: Long): AlarmWithDate {
        return dao.getAlarmWithDate(index)
    }

    override fun selectAllAlarmList(): Flow<List<AlarmWithDate>> {
        return dao.getAllAlarmList()
    }

    override suspend fun insertAlarm(alarm: Alarm, alarmDate: List<AlarmDate>) {
        dao.insertAlarm(alarm, alarmDate)
    }
}