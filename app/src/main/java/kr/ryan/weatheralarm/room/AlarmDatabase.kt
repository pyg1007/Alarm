package kr.ryan.weatheralarm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.InternalWeather
import kr.ryan.weatheralarm.data.IsWeatherUpdate
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
@Database(entities = [Alarm::class, AlarmDate::class, InternalWeather::class, IsWeatherUpdate::class], version = 1)
@TypeConverters(value = [DateConverter::class, JsonConverter::class])
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao
    abstract fun weatherDao() : WeatherDao
    abstract fun isWeatherDao() : IsUpdateDao

    companion object {

        fun getInstance(context: Context): AlarmDatabase =
            Room.databaseBuilder(context, AlarmDatabase::class.java, "Alarm.db").build()

    }

}