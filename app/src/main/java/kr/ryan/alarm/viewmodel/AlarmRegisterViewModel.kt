package kr.ryan.alarm.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.Days
import kr.ryan.alarm.repository.AlarmRepository

/**
 * Alarm
 * Class: AlarmRegisterViewModel
 * Created by pyg10.
 * Created On 2021-08-15.
 * Description:
 */
class AlarmRegisterViewModel(private val repository: AlarmRepository) : ViewModel() {

    private val selectedItem = mutableListOf<Days>()
    private val _selectedDays = MutableLiveData<List<Days>>()
    val selectedDays = Transformations.map(_selectedDays){
        it.sortedBy { days -> days.calendarIndex }
    }

    val dayClicked = fun(days: Days){
        checkDuplicationDay(days)
    }

    private fun checkDuplicationDay(days: Days) = viewModelScope.launch(Dispatchers.Default){
        if (selectedItem.filter { it.day == days.day }.isNullOrEmpty()) addDay(days)
        else removeDay(days)
        withContext(Dispatchers.Main){
            _selectedDays.value = selectedItem
        }
    }

    private fun addDay(days: Days) {
        selectedItem.add(days)
    }

    private fun removeDay(days: Days) {
        selectedItem.remove(days)
    }

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAlarm(alarm)
    }

}