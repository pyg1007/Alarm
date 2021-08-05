package kr.ryan.alarm.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import kr.ryan.alarm.data.Alarm

/**
 * Alarm
 * Class: AlarmDao
 * Created by pyg10.
 * Created On 2021-08-05.
 * Description:
 */

@Dao
interface AlarmDao {

    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

}