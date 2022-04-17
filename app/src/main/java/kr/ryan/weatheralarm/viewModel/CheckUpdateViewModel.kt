package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.IsWeatherUpdate
import kr.ryan.weatheralarm.usecase.IsWeatherUpdateSelectUseCase
import kr.ryan.weatheralarm.usecase.IsWeatherUpdateUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: IsUpdatedViewModel
 * Created by pyg10.
 * Created On 2022-04-17.
 * Description:
 */
@HiltViewModel
class CheckUpdateViewModel @Inject constructor(
    private val isWeatherUpdateSelectUseCase: IsWeatherUpdateSelectUseCase,
    private val isWeatherUpdateUseCase: IsWeatherUpdateUseCase
) : ViewModel() {

    val isWeatherUpdate = flow {
        emit(isWeatherUpdateSelectUseCase.selectIsUpdateWeather())
    }.catch {
        Timber.d("$it")
    }

    fun completeWeatherUpdate(isWeatherUpdate: IsWeatherUpdate) = viewModelScope.launch {
        isWeatherUpdateUseCase.isUpdateWeather(isWeatherUpdate)
    }

}