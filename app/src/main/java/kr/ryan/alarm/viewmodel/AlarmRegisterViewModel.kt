package kr.ryan.alarm.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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

    private val currentDate = Date()

    private val day = arrayOf("일", "월", "화", "수", "목", "금", "토")

    private val _selectedDate = MutableLiveData(Date())
    val selectedDate = Transformations.map(_selectedDate) {
        val currentCalendar = Calendar.getInstance().apply {
            time = currentDate
        }
        val selecteCalendar = Calendar.getInstance().apply { }
    }

    private val _selectedDay = MutableLiveData<List<Date>>()
    val selectedDay = Transformations.map(_selectedDay) {
        it.map { date ->
            Calendar.getInstance().apply {
                time = date
            }.get(Calendar.DAY_OF_WEEK)
        }.sorted()
    }

    val showSelectedDay = Transformations.map(selectedDay) {
        it.joinToString(", ") { i -> day[i - 1] }
    }

    private val _iconClickStatus = MutableLiveData(false)
    val iconClickStatus: LiveData<Boolean>
        get() = _iconClickStatus

    fun calendarClick() { // 달력을 클릭
        _iconClickStatus.value = true
    }

    fun clearCalendarStatus() { // 달력 클릭후 다시 init상태로 되돌리기 위한 메소드
        _iconClickStatus.value = false
    }

    init {
        changeDayDate()
    }

    private fun changeDayDate() = viewModelScope.launch(Dispatchers.Default) {
        _selectedDate.asFlow().collect {
            val list = mutableListOf<Date>()
            val calendar = Calendar.getInstance().apply {
                time = it
            }

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val min = calendar.get(Calendar.MINUTE)

            _selectedDay.value?.let { item ->
                item.forEach { date ->
                    list.add(Calendar.getInstance().apply {
                        time = date
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, min)
                    }.time)
                }
                withContext(Dispatchers.Main) {
                    _selectedDay.value = list
                }
            }
        }
    }

    fun changedTime(date: Date) { // 달력 다이얼로그에서 선택한 후에 날짜가 바뀜을 알려주는 메소드 또는 Time Picker에서 변경이되었을때 알려주는 메소드
        _selectedDate.value = date
    }

    val changeTime = fun(date: Date) { // Time Picker Change 시 동작하는 메소드
        changedTime(date)
    }

    val dayClicked = fun(day: Int) { // 요일 클릭하면 동작하는 메소드
        checkDuplicationDay(day)
    }

    private fun checkDuplicationDay(day: Int) {
        _selectedDay.value?.let {
            val existDate = it.map { date ->
                Calendar.getInstance().apply {
                    time = date
                }.get(Calendar.DAY_OF_WEEK)
            }.find { i -> i == day }

            val item = it.toMutableList()
            if (existDate == null) {
            }
        }
    }

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAlarm(alarm)
    }

}