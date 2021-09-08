package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

/**
 * Alarm
 * Class: FlowViewModel
 * Created by pyg10.
 * Created On 2021-09-08.
 * Description:
 */
class FlowViewModel : ViewModel() {

    private val arrayDay = arrayOf("일", "월", "화", "수", "목", "금", "토")

    private val _selectedDay = MutableStateFlow<List<Date>>(mutableListOf())

    private val _selectedDate = MutableStateFlow(Date())

    private val _showSelectDay = MutableStateFlow("")
    val showSelectDay = _showSelectDay.asStateFlow()

    init {

        viewModelScope.launch {
            _selectedDay.collect {
                _showSelectDay.emit(it.joinToString(", ") { date ->
                    arrayDay[Calendar.getInstance().apply {
                        time = date
                    }.get(Calendar.DAY_OF_WEEK)-1]
                })
            }
        }
        
    }

    private fun emitTest() = viewModelScope.launch {
        repeat(7){
            Log.e("ViewModel", "repeat $it")
            val days = if (_selectedDay.value.isNullOrEmpty()) mutableListOf() else _selectedDay.value.toMutableList()
            days.add(Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, it+1)
            }.time)
            _selectedDay.emit(days)
        }
    }



}