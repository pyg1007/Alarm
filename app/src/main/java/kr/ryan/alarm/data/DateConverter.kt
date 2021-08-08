package kr.ryan.alarm.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Alarm
 * Class: RoomConverter
 * Created by pyg10.
 * Created On 2021-08-09.
 * Description:
 */

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(long: Long?): Date? {
        return long?.let { Date(it) }
    }
}