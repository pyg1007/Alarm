package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.retrofitmodule.NetWorkResult
import kr.ryan.weatheralarm.data.CheckWeatherUpdated
import kr.ryan.weatheralarm.data.InternalWeather
import kr.ryan.weatheralarm.data.Mapping.convertWeatherToInternalWeather
import kr.ryan.weatheralarm.domain.usecase.CheckWeatherUpdatedUseCase
import kr.ryan.weatheralarm.domain.usecase.WeatherInsertUseCase
import kr.ryan.weatheralarm.domain.usecase.WeatherSelectUseCase
import kr.ryan.weatheralarm.domain.usecase.WeatherUseCase
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

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
    private val insertWeatherUseCase: WeatherInsertUseCase,
    private val checkWeatherUpdatedUseCase: CheckWeatherUpdatedUseCase
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
                    checkWeatherUpdatedUseCase(CheckWeatherUpdated(index = 1, date = Date(), isUpdate = true))
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