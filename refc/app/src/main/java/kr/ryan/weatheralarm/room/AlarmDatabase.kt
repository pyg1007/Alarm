package kr.ryan.weatheralarm.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.ryan.weatheralarm.data.Alarm

/**
 * WeatherAlarm
 * Class: AlarmDatabase
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
@Database(entities = [Alarm::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}