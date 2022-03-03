package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.data.InternalWeather
import kr.ryan.weatheralarm.data.Mapping.convertWeatherToInternalWeather
import kr.ryan.weatheralarm.usecase.WeatherInsertUseCase
import kr.ryan.weatheralarm.usecase.WeatherSelectUseCase
import kr.ryan.weatheralarm.usecase.WeatherUseCase
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.TO_GRID
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.convertGRIDTOGPS
import timber.log.Timber
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
    private val selectWeatherUseCase: WeatherSelectUseCase,
    private val insertWeatherUseCase: WeatherInsertUseCase
): ViewModel(){

    val weather = flow {
        selectWeatherUseCase.selectWeatherInfo().collect {
            emit(it)
        }
    }.catch {
        Timber.d("Exception $it")
    }

    /**
     *
     * param = serviceKey, nx, ny, dataType, base_date, base_time
     *
     */

    fun weatherInfo(param: HashMap<String, String>, latXLngY: CalculatorLatitudeAndLongitude.LatXLngY, complete: () -> Unit) = viewModelScope.launch{
        when(val result = weatherUseCase.getWeatherInfo(param)){
            is NetWorkResult.Success -> {
                result.data.convertWeatherToInternalWeather()?.let {
                    val weatherInfo = InternalWeather(it.index, it.date, latXLngY.lat.toInt(), latXLngY.lng.toInt(), it.item)
                    insertWeatherUseCase.insertWeatherInfo(weatherInfo)
                }
                complete()
            }

            else -> {
                complete()
            }
        }
    }

}