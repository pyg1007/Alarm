package kr.ryan.weatheralarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * WeatherAlarm
 * Class: IsWeatherUpdate
 * Created by pyg10.
 * Created On 2022-04-13.
 * Description:
 */
@Entity
data class IsWeatherUpdate(
    @PrimaryKey
    val index: Int = 0,
    val date: Date,
    val isUpdate: Boolean
){
    companion object{
        private const val DEFAULT_INDEX = 1
        val DEFAULT_IS_WEATHER_UPDATE = IsWeatherUpdate(DEFAULT_INDEX, Date(),false)
    }
}
