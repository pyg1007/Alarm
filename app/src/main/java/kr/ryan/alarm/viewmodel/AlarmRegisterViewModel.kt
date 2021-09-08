package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.AlarmStatus
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.utility.compareDate
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

    private val day = arrayOf("일", "월", "화", "수", "목", "금", "토")
    private var isDateAlarm = 0

    private val _selectedDate = MutableLiveData(Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
    }.time)

    val selectedDate = Transformations.map(_selectedDate) {
        "${it.compareDate()}${it.dateToString("MM월 dd일 (E)")}"
    }

    private val _alarmStatus = MutableLiveData(AlarmStatus.INIT)
    val alarmStatus: LiveData<AlarmStatus>
        get() = _alarmStatus

    private var _alarmTitle = ""

    fun changeAlarmTitle(value: String) {
        _alarmTitle = value
    }

    val checkDate
        get() = _selectedDate

    val checkDay
        get() = _selectedDay

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

                setLiveDataValue(_selectedDay, list)
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

    private fun checkDuplicationDay(day: Int) = viewModelScope.launch(Dispatchers.Default) {
        _selectedDay.value?.also { item ->
            val exist = item.map {
                Calendar.getInstance().apply {
                    time = it
                }.get(Calendar.DAY_OF_WEEK)
            }.find { i -> i == day } ?: 999

            val dateList = item.toMutableList()

            if (exist == 999) { // 존재하지않는다면 추가
                dateList.add(Calendar.getInstance().apply {
                    time = _selectedDate.value!! // 값을 바로넣어주기떄문에 널일수가 없음!
                    set(Calendar.DAY_OF_WEEK, day)
                }.time)
                isDateAlarm++
            } else { // 존재한다면 삭제
                dateList.remove(Calendar.getInstance().apply {
                    time = _selectedDate.value!! // 값을 바로넣어주기떄문에 널일수가 없음!
                    set(Calendar.DAY_OF_WEEK, day)
                }.time)
                isDateAlarm--
            }

            setLiveDataValue(_selectedDay, dateList)
        } ?: run { // 값이 비어있다면 아무것도 없는 경우기때문에 이쪽 구문을 타게됌
            val dateList = mutableListOf<Date>()
            dateList.add(Calendar.getInstance().apply {
                time = _selectedDate.value!! // 값을 바로넣어주기떄문에 널일수가 없음!
                set(Calendar.DAY_OF_WEEK, day)
            }.time)
            isDateAlarm++

            setLiveDataValue(_selectedDay, dateList)
        }
    }

    fun registerAlarm() = viewModelScope.launch(Dispatchers.Default) {
        val alarmDate = runCatching {
            _selectedDay.value!!
        }.getOrDefault(mutableListOf(_selectedDate.value!!))

        val isSingle = isDateAlarm == 1
        val alarm = Alarm(isSingle, _alarmTitle, alarmDate, true)
        insertAlarm(alarm)

        setLiveDataValue(_alarmStatus, AlarmStatus.REGISTER)
    }

    fun cancelAlarm() {
        setLiveDataValue(_alarmStatus, AlarmStatus.CANCEL)
    }

    fun initAlarmStatus() {
        setLiveDataValue(_alarmStatus, AlarmStatus.INIT)
    }

    private fun insertAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.insertAlarm(alarm)
    }

    private fun <T> setLiveDataValue(liveData: MutableLiveData<T>, value: T) =
        viewModelScope.launch(Dispatchers.Main) {
            liveData.value = value
        }

}