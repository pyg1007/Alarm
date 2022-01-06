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

    override suspend fun insertAlarm(alarmWithDate: AlarmWithDate) {
        dao.insertAlarm(alarmWithDate)
    }

    override suspend fun deleteAlarm(alarmWithDate: AlarmWithDate) {
        dao.deleteAlarm(alarmWithDate)
    }

    override suspend fun updateAlarm(alarmWithDate: AlarmWithDate) {
        dao.updateAlarm(alarmWithDate)
    }

    override suspend fun insertAlarmDate(vararg alarmDate: AlarmDate) {
        dao.insertAlarmDate(*alarmDate)
    }

    override suspend fun updateAlarmDate(vararg alarmDate: AlarmDate) {
        dao.updateAlarmDate(*alarmDate)
    }

    override suspend fun deleteAlarmDate(vararg alarmDate: AlarmDate) {
        dao.deleteAlarmDate(*alarmDate)
    }

    override suspend fun updateAlarmInfo(alarm: Alarm) {
        dao.updateAlarmInfo(alarm)
    }

    override fun getAllAlarmList(): Flow<List<AlarmWithDate>> {
        return dao.getAllAlarmList()
    }
}