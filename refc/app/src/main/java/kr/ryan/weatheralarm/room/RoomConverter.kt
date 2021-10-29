package kr.ryan.weatheralarm.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

/**
 * WeatherAlarm
 * Class: RoomConverter
 * Created by pyg10.
 * Created On 2021-10-28.
 * Description:
 */
object RoomConverter {

    @TypeConverter
    fun fromTimestamp(value: List<Date>?): String? = Gson().toJson(value?.map { it.time })

    @TypeConverter
    fun dateToTimeStamp(value: String?): List<Date> = Gson().fromJson(value, Array<Long>::class.java).map { Date(it) }

}