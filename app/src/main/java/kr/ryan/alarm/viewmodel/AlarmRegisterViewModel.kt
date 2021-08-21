package kr.ryan.alarm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ryan.alarm.data.Alarm
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

    private val selectedItem = mutableListOf<Date>()
    private var selectedDate = Date()
    private val _selectedDays = MutableLiveData<List<Date>>()
    val selectedDays = Transformations.map(_selectedDays) {
        it.map { date ->
            Calendar.getInstance().apply {
                time = date
            }.get(Calendar.DAY_OF_WEEK)
        }.sorted()
    }

    private val day = arrayOf("일", "월", "화", "수", "목", "금", "토")


    val selectedShowDays = Transformations.map(_selectedDays) {
        it.map { date ->
            Calendar.getInstance().apply {
                time = date
            }.get(Calendar.DAY_OF_WEEK)
        }.sorted().joinToString(", "){dayOfWeek -> day[dayOfWeek - 1]}
    }

    val dayClicked = fun(day: Int) {
        checkDuplicationDay(day)
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