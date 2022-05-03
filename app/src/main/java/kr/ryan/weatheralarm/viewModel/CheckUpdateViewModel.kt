package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.CheckWeatherUpdated
import kr.ryan.weatheralarm.domain.usecase.CheckWeatherUpdatedSelectUseCase
import kr.ryan.weatheralarm.domain.usecase.CheckWeatherUpdatedUseCase
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
    private val checkWeatherUpdatedSelectUseCase: CheckWeatherUpdatedSelectUseCase,
    private val checkWeatherUpdatedUseCase: CheckWeatherUpdatedUseCase
) : ViewModel() {

    val checkUpdateWeather = flow {
        checkWeatherUpdatedSelectUseCase().collect {
            emit(it)
        }
    }.catch {
        Timber.d("$it")
    }

    fun changeWeatherUpdate(checkWeatherUpdated: CheckWeatherUpdated) = viewModelScope.launch {
        checkWeatherUpdatedUseCase(checkWeatherUpdated)
    }

}