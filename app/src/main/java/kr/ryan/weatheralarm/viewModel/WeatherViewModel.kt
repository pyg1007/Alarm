package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
import java.net.SocketException
import java.net.SocketTimeoutException
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
    
    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?>
        get() = _error.asStateFlow()

    val weather = flow {
        selectWeatherUseCase.selectWeatherInfo().collect {
            emit(it)
        }
    }.catch {
        _error.emit(it)
        Timber.d("Exception $it")
    }

    /**
     *
     * param = serviceKey, nx, ny, numOfRows, dataType, base_date, base_time
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

            is NetWorkResult.NetWorkError -> {
                Timber.d(" NetWorkError Error  - > ${result.throwable}")
                complete()
            }

            is NetWorkResult.ApiError -> {
                Timber.d(" Api Error  - > error Code : ${result.code} error Message : ${result.message}")
                complete()
            }

            else -> {
                complete()
            }
        }
    }

}