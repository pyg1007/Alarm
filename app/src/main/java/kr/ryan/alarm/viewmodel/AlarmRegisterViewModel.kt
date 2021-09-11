package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.UiStatus
import kr.ryan.alarm.repository.AlarmRepository
import java.util.*

/**
 * Alarm
 * Class: AlarmRegisterViewModel
 * Created by pyg10.
 * Created On 2021-08-15.
 * Description:
 */
class AlarmRegisterViewModel(private val repository: AlarmRepository) : ViewModel() {

    private var isDateAlarm = 0

    private val arrayDay = arrayOf("일", "월", "화", "수", "목", "금", "토")

    private val _selectedDay = MutableStateFlow<List<Date>>(mutableListOf())

    private val _selectedDate = MutableStateFlow(Date())

    private val _title = MutableStateFlow("")

    private val _showSelectDay = MutableStateFlow("")
    private val _createCircleDay = MutableStateFlow(listOf<Int>())
    val createCircleDay = _createCircleDay.asStateFlow()

    private val _uiStatus = MutableStateFlow<UiStatus>(UiStatus.Init(Unit))
    val uiStatus = _uiStatus.asStateFlow()

    fun onClickInsertAlarm() = viewModelScope.launch {
        _uiStatus.emit(UiStatus.Loading(Unit))
        Log.e("ViewModel", "${uiStatus.value}")
        insertAlarm()
        Log.e("ViewModel", "Insert")
        _uiStatus.emit(UiStatus.Complete(Unit))
        Log.e("ViewModel", "${uiStatus.value}")
    }

    fun onClickCancelAlarm() = viewModelScope.launch {
        _uiStatus.emit(UiStatus.Complete(Unit))
    }

    fun initUiStatus() = viewModelScope.launch {
        _uiStatus.emit(UiStatus.Init(Unit))
    }

    init {

        showingSelectedDay()

    }

    fun changeTitle(title: String) = viewModelScope.launch {
        _title.emit(title)
    }

    val changeTime = fun(date: Date) { // Time Picker Change 시 동작하는 메소드
        changeSelectedDate(date)
    }

    val dayClicked = fun(day: Int) { // 요일 클릭하면 동작하는 메소드
        checkDuplicationDay(day)
    }

    private fun changeSelectedDate(date: Date) = viewModelScope.launch {
        _selectedDate.emit(date)
    }

    private fun showingSelectedDay() = viewModelScope.launch {

        _selectedDay.collect {
            val dateToInt = it.map { date ->
                Calendar.getInstance().apply {
                    time = date
                }.get(Calendar.DAY_OF_WEEK) - 1
            }.sorted()

            _createCircleDay.emit(dateToInt)
            _showSelectDay.emit(dateToInt.joinToString(", ") { date -> arrayDay[date - 1] })
        }

    }

    private fun checkDuplicationDay(day: Int) = viewModelScope.launch {
        _selectedDay.value.also {
            var dateList = mutableListOf<Date>()
            if (it.isNullOrEmpty()) { // 이전에 선택된 날짜가 없는경우

                dateList.add(Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_WEEK, day)
                }.time)

                isDateAlarm++

                _selectedDay.emit(dateList)
            } else {

                val existDay = it.filter { date ->
                    Calendar.getInstance().apply {
                        time = date
                    }.get(Calendar.DAY_OF_WEEK) == day
                }

                if (existDay.isNullOrEmpty()) { // 선택된 날짜가 없는경우 추가
                    dateList.add(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_WEEK, day)
                    }.time)

                    isDateAlarm++

                    _selectedDay.emit(dateList)
                } else { // 선택된 날짜가 있는경우 삭제
                    dateList = it.toMutableList()

                    dateList.remove(Calendar.getInstance().apply {
                        set(Calendar.DAY_OF_WEEK, day)
                    }.time)

                    isDateAlarm--

                    _selectedDay.emit(dateList)

                }
            }
        }
    }

    private suspend fun insertAlarm() = viewModelScope.launch {

        val date =
            if (_selectedDay.value.isNullOrEmpty()) listOf(_selectedDate.value) else _selectedDay.value

        val alarm = Alarm(isDateAlarm == 0, _title.value, date, true)

        repository.insertAlarm(alarm)

    }

}