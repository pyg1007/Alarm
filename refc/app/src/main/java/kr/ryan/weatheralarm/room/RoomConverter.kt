package kr.ryan.weatheralarm.room

import androidx.room.TypeConverter
import java.util.*

/**
 * WeatherAlarm
 * Class: RoomConverter
 * Created by pyg10.
 * Created On 2021-10-28.
 * Description:
 */
class RoomConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(value: Date?): Long? {
        return value?.time
    }

}