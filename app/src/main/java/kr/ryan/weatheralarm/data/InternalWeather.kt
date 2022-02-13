package kr.ryan.weatheralarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * WeatherAlarm
 * Class: InternalWeather
 * Created by pyg10.
 * Created On 2022-02-12.
 * Description:
 */
@Entity
data class InternalWeather(
    @PrimaryKey(autoGenerate = true)
    val index: Long = 0,
    val date: Date,
    val nx: Int,
    val ny: Int,
    val item: List<InternalItem>
)

data class InternalItem(
    val category: String,
    val value: String
)
