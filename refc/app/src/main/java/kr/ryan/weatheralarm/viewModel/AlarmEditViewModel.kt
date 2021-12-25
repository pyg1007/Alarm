package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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
    private val updateUseCase: AlarmUpdateUseCase
) : ViewModel() {

    private var selectedDaysStatus = mutableListOf(false, false, false, false, false, false, false)

    private val _dayStatus = MutableStateFlow(selectedDaysStatus)
    val dayStatus = _dayStatus.asStateFlow()

    private val _cancelEvent = MutableStateFlow(false)
    val cancelEvent = _cancelEvent.asStateFlow()

    private val _saveEvent = MutableStateFlow(false)
    val saveEvent = _saveEvent.asStateFlow()

    val dates = MutableStateFlow(listOf<Date>())
    val title = MutableStateFlow("")
    val hour = MutableStateFlow(Date().getCurrentHour())
    val min = MutableStateFlow(Date().getCurrentMin())

    val dayClick = fun(day: Int){
        Timber.d("$day")
    }

    fun changeSelectedDaysStatus(index: Int) = viewModelScope.launch {
        val changeDayStatus = selectedDaysStatus.toMutableList()
        changeDayStatus[index] = !changeDayStatus[index]
        selectedDaysStatus = changeDayStatus
        _dayStatus.emit(changeDayStatus)
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

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch {
        insertUseCase.insertAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch {
        updateUseCase.updateAlarm(alarm)
    }

}