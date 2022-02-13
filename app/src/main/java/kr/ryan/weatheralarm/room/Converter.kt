package kr.ryan.weatheralarm.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import kr.ryan.weatheralarm.data.InternalItem
import java.util.*

/**
 * WeatherAlarm
 * Class: RoomConverter
 * Created by pyg10.
 * Created On 2021-10-28.
 * Description:
 */
class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(value: Date?): Long? {
        return value?.time
    }

}

class JsonConverter {

    @TypeConverter
    fun listToJson(value: List<InternalItem>): String? = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<InternalItem> = Gson().fromJson(value, Array<InternalItem>::class.java).toList()
}