package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.data.Mapping.convertWeatherToInternalWeather
import kr.ryan.weatheralarm.usecase.WeatherInsertUseCase
import kr.ryan.weatheralarm.usecase.WeatherUseCase
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: WeatherViewModel
 * Created by pyg10.
 * Created On 2022-02-11.
 * Description:
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val insertWeatherUseCase: WeatherInsertUseCase
): ViewModel(){

    /**
     *
     * param = serviceKey, nx, ny, numOfRows, pageNo, dataType, base_date, base_time
     *
     */

    fun weatherInfo(param: HashMap<String, String>) = viewModelScope.launch{
        when(val result = weatherUseCase.getWeatherInfo(param)){
            is NetWorkResult.Success -> {
                result.data.convertWeatherToInternalWeather()?.let {
                    insertWeatherUseCase.insertWeatherInfo(it)
                }
            }

            else -> {

            }
        }
    }

}