package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.convertDateString
import kr.ryan.weatheralarm.util.convertDayString
import kr.ryan.weatheralarm.util.getCurrentHour
import kr.ryan.weatheralarm.util.getCurrentMin
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmEditViewModel
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
@HiltViewModel
class AlarmEditViewModel @Inject constructor(
    private val insertUseCase: AlarmInsertUseCase,
    private val updateUseCase: AlarmUpdateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val dayList = listOf("일", "월", "화", "수", "목", "금", "토")

    private val _selectedHour = Channel<Int>()

    private val _selectedMinute = Channel<Int>()

    val selectedHour = _selectedHour.receiveAsFlow().asLiveData()
    val selectedMinute = _selectedMinute.receiveAsFlow().asLiveData()

    fun changeHour(hour: Int) = viewModelScope.launch {
        _selectedHour.send(hour)
    }

    fun changeMinute(minute: Int) = viewModelScope.launch {
        _selectedMinute.send(minute)
    }

    fun onClickDays(index: Int) = viewModelScope.launch {

    }

}