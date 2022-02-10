package kr.ryan.weatheralarm.data

import com.google.gson.annotations.SerializedName

/**
 * WeatherAlarm
 * Class: Weather
 * Created by pyg10.
 * Created On 2022-02-10.
 * Description:
 */

data class Weather(val response: WeatherResponse)

data class WeatherResponse(
    @SerializedName("header")
    val weatherHeader: WeatherHeader,
    @SerializedName("body")
    val weatherBody: WeatherBody,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class WeatherHeader(
    val resultCode: String,
    val resultMsg: String
)

data class WeatherBody(
    val dataType: String,
    @SerializedName("items")
    val weatherItems: WeatherItems
)

data class WeatherItems(
    @SerializedName("item")
    val weatherItem: List<WeatherItem>
)

data class WeatherItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue: String
)