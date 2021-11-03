package kr.ryan.weatheralarm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import kr.ryan.weatheralarm.data.Alarm
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors

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

    companion object {

        fun getInstance(context: Context): AlarmDatabase =
            Room.databaseBuilder(context, AlarmDatabase::class.java, "Alarm.db").build()

    }

}