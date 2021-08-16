package kr.ryan.alarm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.DateConverter
import kr.ryan.alarm.room.dao.AlarmDao

/**
 * Alarm
 * Class: ApplicationDatabase
 * Created by pyg10.
 * Created On 2021-08-05.
 * Description:
 */

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: ApplicationDatabase

        fun getInstance(context: Context): ApplicationDatabase {
            if (!::INSTANCE.isInitialized) INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java,
                "Alarm_DB"
            ).build()
            return INSTANCE
        }


    }

}