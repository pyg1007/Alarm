package kr.ryan.weatheralarm.retrofit

import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.data.Weather
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * WeatherAlarm
 * Class: WeatherService
 * Created by pyg10.
 * Created On 2022-02-11.
 * Description:
 */
interface WeatherService {

    @GET("getVilageFcst")
    suspend fun getWeatherInfo(@QueryMap params: HashMap<String, String>) : NetWorkResult<Weather>

}