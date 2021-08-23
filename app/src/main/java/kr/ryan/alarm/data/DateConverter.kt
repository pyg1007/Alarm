package kr.ryan.alarm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
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
    fun dateToLong(value: List<Date>?): String? = Gson().toJson(value?.map { it.time })

    @TypeConverter
    fun longToDate(value: String?): List<Date> = Gson().fromJson(value, Array<Long>::class.java).map { Date(it) }
}