package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.*
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

    private val dayList = listOf("일", "월", "화", "수", "목", "금", "토")

    private val _selectedHour = MutableStateFlow(Date().getCurrentHour())
    private val _selectedMinute = MutableStateFlow(Date().getCurrentMin())

    private val _selectedYear = MutableStateFlow(Date().getCurrentYear())
    private val _selectedMonth = MutableStateFlow(Date().getCurrentMonth())
    private val _selectedDate = MutableStateFlow(Date().getCurrentDate())
    private val _test = MutableStateFlow(0)

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())

    /**
     *
     * 요일들을 클릭했을때 보여주는 부분
     *
     */

    val selectedDays = _selectedDays.map {
        val convertStringList = mutableListOf<String>()
        it.forEach { index ->
            convertStringList.add(dayList[index])
        }
        convertStringList.joinToString(", ")
    }.asLiveData()

    private val selectedDayList = mutableListOf<Int>()

    /**
     *
     * 요일들은 위의 변수로 표기
     * 해당 내용은 선택되어있는 날짜
     * 년, 월, 일, 시, 분을 조절하면 텍스트 변경되는 부분
     *
     */

    val showDate = combine(
        _selectedYear,
        _selectedMonth,
        _selectedDate,
        _selectedHour,
        _selectedMinute
    ) { ints: Array<Int> ->
        Calendar.getInstance().apply {
            set(Calendar.YEAR, ints[0])
            set(Calendar.MONTH, ints[1] - 1)
            set(Calendar.DAY_OF_MONTH, ints[2])
            set(Calendar.HOUR_OF_DAY, ints[3])
            set(Calendar.MINUTE, ints[4])
        }.time
    }.map {
        it.convertDateWithDayToString()
    }.asLiveData()

    /**
     *
     * 요일 클릭시 동작하는 부분
     *
     */
    val onClickDays = { index: Int ->
        viewModelScope.launch {
            if (selectedDayList.contains(index))
                selectedDayList.remove(index)
            else
                selectedDayList.add(index)

            _selectedDays.emit(selectedDayList.sorted().toList())
        }
    }

    val alarmTitle = MutableStateFlow("")

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

    suspend fun insert(success: () -> Unit, failure: (t: Throwable) -> Unit) =
        viewModelScope.launch {
            runCatching {
                val alarm = Alarm(title = alarmTitle.value, onOff = true)
                val alarmDate: List<AlarmDate> = if (_selectedDays.value.isNullOrEmpty()) {
                    listOf(AlarmDate(date = Calendar.getInstance().apply {
                        set(Calendar.YEAR, _selectedYear.value)
                        set(Calendar.MONTH, _selectedMonth.value - 1)
                        set(Calendar.DAY_OF_MONTH, _selectedDate.value)
                        set(Calendar.HOUR_OF_DAY, _selectedHour.value)
                        set(Calendar.MINUTE, _selectedMinute.value)
                    }.time))
                } else {
                    val dateList = mutableListOf<AlarmDate>()
                    _selectedDays.value.forEach {
                        dateList.add(AlarmDate(date = Calendar.getInstance().apply {
                            set(Calendar.YEAR, _selectedYear.value)
                            set(Calendar.MONTH, _selectedMonth.value - 1)
                            set(Calendar.DAY_OF_WEEK, it + 1)
                            set(Calendar.HOUR_OF_DAY, _selectedHour.value)
                            set(Calendar.MINUTE, _selectedMinute.value)
                        }.time))
                    }
                    dateList.toList()
                }

                insertUseCase.insertAlarm(alarm, alarmDate)
            }.onSuccess {
                success()
            }.onFailure {
                failure(it)
            }
        }


}