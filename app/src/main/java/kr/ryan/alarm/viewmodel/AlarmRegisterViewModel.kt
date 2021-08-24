package kr.ryan.alarm.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.utility.dateToString
import java.util.*

/**
 * Alarm
 * Class: AlarmRegisterViewModel
 * Created by pyg10.
 * Created On 2021-08-15.
 * Description:
 */
class AlarmRegisterViewModel(private val repository: AlarmRepository) : ViewModel() {

    private val day = arrayOf("일", "월", "화", "수", "목", "금", "토") // 요일들 배열로 지정

    private var selectedItem = mutableListOf<Date>()

    private var selectedDate = Date() // 날짜

    private val _selectedDays = MutableLiveData<List<Date>>()
    val selectedDays = Transformations.map(_selectedDays) {
        it.map { date ->
            Calendar.getInstance().apply {
                time = date
            }.get(Calendar.DAY_OF_WEEK)
        }.sorted()
    }

    val selectedShowDays = Transformations.map(_selectedDays) { // 요일 선택시 보여지는 요일들 (일, 월, 화) 등등
        if (it.size == 1) {
            it[0].dateToString("MM월 dd일 (E)")
        } else {
            it.map { date ->
                Calendar.getInstance().apply {
                    time = date
                }.get(Calendar.DAY_OF_WEEK)
            }.sorted().joinToString(", ") { dayOfWeek -> day[dayOfWeek - 1] }
        }
    }

    init {

        selectedItem.add(selectedDate)

    }

    val changeTime = fun(date: Date){
        changeDate(date)
    }

    val dayClicked = fun(day: Int) {
        checkDuplicationDay(day)
    }

    private var _clickCalendarIcon = MutableLiveData<Boolean>()
    val clickCalendarIcon: LiveData<Boolean>
        get() = _clickCalendarIcon

    fun calendarIconClick() {
        _clickCalendarIcon.value = true
    }

    fun clearCalendarIconClick() {
        _clickCalendarIcon.value = false
    }

    fun changeDate(date: Date) = viewModelScope.launch(Dispatchers.Default) {
        selectedDate = date
        selectedItem.clear()
        selectedItem.add(selectedDate)
        withContext(Dispatchers.Main) {
            _selectedDays.value = selectedItem
        }

    }

    private fun checkDuplicationDay(day: Int) = viewModelScope.launch(Dispatchers.Default) {
        val calendar = Calendar.getInstance().apply {
            time = selectedDate
            set(Calendar.DAY_OF_WEEK, day)
        }

        val currentDate = Date()

        if (currentDate > calendar.time) calendar.add(Calendar.DAY_OF_MONTH, 7)

        if (selectedItem.filter {
                Calendar.getInstance().apply {
                    time = it
                }.get(Calendar.DAY_OF_WEEK) == calendar.get(Calendar.DAY_OF_WEEK)
            }.isNullOrEmpty())
            addDay(calendar.time)
        else
            removeDay(calendar.time)

        withContext(Dispatchers.Main) {
            _selectedDays.value = selectedItem
        }
    }

    private fun addDay(date: Date) {
        selectedItem.add(date)
    }

    private fun removeDay(date: Date) {
        selectedItem.remove(date)
    }

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAlarm(alarm)
    }

}