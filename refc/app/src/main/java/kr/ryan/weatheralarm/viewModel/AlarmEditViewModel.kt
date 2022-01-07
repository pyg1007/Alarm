package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val showDayList = mutableListOf<String>()

    private val _observeShowDayList = MutableStateFlow<List<String>>(listOf())
    val observeShowDayList = MutableStateFlow("")

    private val _cancelEvent = MutableStateFlow(false)
    val cancelEvent = _cancelEvent.asStateFlow()

    private val _saveEvent = MutableStateFlow(false)
    val saveEvent = _saveEvent.asStateFlow()

    val dates = MutableStateFlow(listOf<Date>())
    val title = MutableStateFlow("")
    val hour = MutableStateFlow(Date().getCurrentHour())
    val min = MutableStateFlow(Date().getCurrentMin())

    val testList = MutableStateFlow(listOf(false, false, false, false, false, false, false))

    val sunDayState = MutableStateFlow(false)
    val monDayState = MutableStateFlow(false)
    val tuesDayState = MutableStateFlow(false)
    val wednesDayState = MutableStateFlow(false)
    val thursDayState = MutableStateFlow(false)
    val friDayState = MutableStateFlow(false)
    val saturDayState = MutableStateFlow(false)

    init {
        sunDayState.controlShowing(0)
        monDayState.controlShowing(1)
        tuesDayState.controlShowing(2)
        wednesDayState.controlShowing(3)
        thursDayState.controlShowing(4)
        friDayState.controlShowing(5)
        saturDayState.controlShowing(6)

        showDay()

        viewModelScope.launch {
            testList.collect {
                Timber.d("$it")
            }
        }
    }

    private fun StateFlow<Boolean>.controlShowing(index: Int) = viewModelScope.launch {
        collect {
            if (it)
                showDayList.add(dayList[index])
            else
                showDayList.remove(dayList[index])

            _observeShowDayList.emit(showDayList.toList())
        }
    }

    private fun showDay() = viewModelScope.launch {
        _observeShowDayList.collect { list ->
            observeShowDayList.emit(list.sortedBy { dayList.indexOf(it) }.joinToString(", "))
        }
    }

    fun changeHour(hour: Int) = viewModelScope.launch {
        this@AlarmEditViewModel.hour.emit(hour)
    }

    fun changeMinute(minute: Int) = viewModelScope.launch {
        min.emit(minute)
    }

    fun changeTitle(title: String) = viewModelScope.launch {
        this@AlarmEditViewModel.title.emit(title)
    }

    fun changeDates(dates: List<Date>) = viewModelScope.launch {
        this@AlarmEditViewModel.dates.emit(dates)
    }

    fun onClickCancelEvent() = viewModelScope.launch {
        _cancelEvent.emit(true)
    }

    fun initCancelEvent() = viewModelScope.launch {
        _cancelEvent.emit(false)
    }

    fun onClickSaveEvent() = viewModelScope.launch {
        _saveEvent.emit(true)
    }

    fun initSaveEvent() = viewModelScope.launch {
        _saveEvent.emit(false)
    }

}