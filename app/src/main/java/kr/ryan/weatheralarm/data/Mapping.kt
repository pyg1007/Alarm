package kr.ryan.weatheralarm.data

import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: Mapping
 * Created by pyg10.
 * Created On 2022-02-13.
 * Description:
 */
object Mapping {

    fun Weather.convertWeatherToInternalWeather() : InternalWeather?{
        return runCatching {
            val date = Calendar.getInstance().apply {
                time = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(response.weatherBody.weatherItems.weatherItem[0].baseDate)!!
            }.time

            InternalWeather(
                date = Calendar.getInstance().apply {
                    time = date
                    set(Calendar.HOUR_OF_DAY, response.weatherBody.weatherItems.weatherItem[0].baseTime.slice(0..1).toInt())
                    set(Calendar.MINUTE, response.weatherBody.weatherItems.weatherItem[0].baseTime.slice(2..3).toInt())
                    set(Calendar.SECOND, 0)
                }.time,
                nx = response.weatherBody.weatherItems.weatherItem[0].nx,
                ny = response.weatherBody.weatherItems.weatherItem[0].ny,
                item = response.weatherBody.weatherItems.weatherItem.map {
                    InternalItem(it.category, it.fcstValue)
                }
            )
        }.getOrNull()
    }

}