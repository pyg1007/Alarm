package kr.ryan.weatheralarm.repository

import androidx.annotation.WorkerThread
import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.data.Weather
import kr.ryan.weatheralarm.retrofit.WeatherService
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherRepository
 * Created by pyg10.
 * Created On 2022-02-11.
 * Description:
 */
class WeatherRepository @Inject constructor(
    private val service: WeatherService
) {

    @WorkerThread
    suspend fun provideWeatherInfo(params : HashMap<String, String>) : NetWorkResult<Weather> = service.getWeatherInfo(params)

}