package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.*
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

    private val _selectedHour = MutableStateFlow(Date().getCurrentHour())
    private val _selectedMinute = MutableStateFlow(Date().getCurrentMin())

    private val _selectedYear = MutableStateFlow(Date().getCurrentYear())
    private val _selectedMonth = MutableStateFlow(Date().getCurrentMonth())
    private val _selectedDate = MutableStateFlow(Date().getCurrentDate())

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())

    val selectedDays = _selectedDays.map{
        val convertStringList = mutableListOf<String>()
        it.forEach { index ->
            convertStringList.add(dayList[index])
        }
        convertStringList.joinToString(", ")
    }.asLiveData()

    private val selectedDayList = mutableListOf<Int>()

    val showDate = combine(_selectedYear, _selectedMonth, _selectedDate, _selectedHour, _selectedMinute){
            ints: Array<Int> ->
        Calendar.getInstance().apply {
            set(Calendar.YEAR, ints[0])
            set(Calendar.MONTH, ints[1]-1)
            set(Calendar.DAY_OF_MONTH, ints[2])
            set(Calendar.HOUR_OF_DAY, ints[3])
            set(Calendar.MINUTE, ints[4])
        }.time
    }.map {
        it.convertDateString()
    }.asLiveData()

    val onClickDays = {index: Int ->
        viewModelScope.launch{
            if (selectedDayList.contains(index))
                selectedDayList.remove(index)
            else
                selectedDayList.add(index)

            _selectedDays.emit(selectedDayList.sorted().toList())
        }
    }

    fun changeHour(hour: Int) = viewModelScope.launch {
        _selectedHour.emit(hour)
    }

    fun changeMinute(minute: Int) = viewModelScope.launch {
        _selectedMinute.emit(minute)
    }

    fun changeYear(year: Int) = viewModelScope.launch {
        _selectedYear.emit(year)
    }

    fun changeMonth(month: Int) = viewModelScope.launch {
        _selectedMonth.emit(month)
    }

    fun changeDate(date: Int) = viewModelScope.launch {
        _selectedDate.emit(date)
    }


}