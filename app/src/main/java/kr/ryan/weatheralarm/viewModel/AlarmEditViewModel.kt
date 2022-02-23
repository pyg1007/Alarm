package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.util.Route.ADD_MODE
import kr.ryan.weatheralarm.util.Route.EDIT_MODE
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
    private val selectUseCase: AlarmSelectUseCase,
    private val insertUseCase: AlarmInsertUseCase,
    private val updateUseCase: AlarmUpdateUseCase,
    private val deleteUseCase: AlarmDeleteUseCase
) : ViewModel() {

    private val _mode = MutableStateFlow(Mode(ADD_MODE))
    private val _preAlarmWithDate = MutableStateFlow<AlarmWithDate?>(null)

    private val _randomPendingNumber = flow {
        selectUseCase.selectAlarmList().map {
            emit(it.map { alarmWithDate ->
                alarmWithDate.alarm.pendingId
            })
        }
    }.catch { e ->
        Timber.d("Exception $e")
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList<Int>())

    private val dayList = listOf("일", "월", "화", "수", "목", "금", "토")

    val selectedHour = MutableStateFlow(Date().getCurrentHour())
    val selectedMinute = MutableStateFlow(Date().getCurrentMin())

    private val _selectedYear = MutableStateFlow(Date().getCurrentYear())
    private val _selectedMonth = MutableStateFlow(Date().getCurrentMonth())
    private val _selectedDate = MutableStateFlow(Date().getCurrentDate())

    val flowSelectedDays = MutableStateFlow(listOf(false, false, false, false, false, false, false))

    fun changeStatus(index: Int) = viewModelScope.launch {
        Timber.d("change -> $index")
        val changeTestList = flowSelectedDays.value.toMutableList()
        changeTestList[index] = !changeTestList[index]
        flowSelectedDays.emit(changeTestList.toList())
    }

    /**
     *
     * 요일들을 클릭했을때 보여주는 부분
     *
     */

    val selectedDays = flowSelectedDays.map {
        val convertStringList = mutableListOf<String>()
        it.forEachIndexed { index, boolean ->
            if (boolean)
                convertStringList.add(dayList[index])
        }
        convertStringList.joinToString(", ")
    }.asLiveData()

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
        selectedHour,
        selectedMinute
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

    val alarmTitle = MutableStateFlow("")

    fun changeHour(hour: Int) = viewModelScope.launch {
        selectedHour.emit(hour)
    }

    fun changeMinute(minute: Int) = viewModelScope.launch {
        selectedMinute.emit(minute)
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

    fun changeDays(days: List<AlarmDate>) = viewModelScope.launch {
        val convertDateToBoolean = MutableList(7) { false }
        days.forEach {

            val index = Calendar.getInstance().apply {
                time = it.date
            }.get(Calendar.DAY_OF_WEEK) - 1

            Timber.d("change Days -> $index")

            convertDateToBoolean[index] = !convertDateToBoolean[index]
        }

        Timber.d("change Days Boolean List -> $convertDateToBoolean")
        flowSelectedDays.emit(convertDateToBoolean)
    }

    fun changeTitle(title: String?) = viewModelScope.launch {
        alarmTitle.emit(title ?: "")
    }

    fun changeMode(mode: Mode) = viewModelScope.launch {
        _mode.emit(mode)
    }

    fun changeAlarmWithDate(alarmWithDate: AlarmWithDate) = viewModelScope.launch {
        _preAlarmWithDate.emit(alarmWithDate)
    }

    fun onActive(success: (alarmWithDate: AlarmWithDate) -> Unit, failure: (t: Throwable) -> Unit) =
        viewModelScope.launch {
            _mode.collect {
                when (it.mode) {
                    ADD_MODE -> {
                        insert({ alarmWithDate ->
                            success(alarmWithDate)
                        }, { failure ->
                            failure(failure)
                        })
                    }
                    EDIT_MODE -> {
                        update({ alarmWithDate ->
                            success(alarmWithDate)
                        }, { failure ->
                            failure(failure)
                        })
                    }
                    else -> {
                        throw IllegalStateException("unKnown Mode")
                    }
                }
            }
        }

    private suspend fun update(
        success: (alarmWithDate: AlarmWithDate) -> Unit,
        failure: (t: Throwable) -> Unit
    ) = viewModelScope.launch {
        runCatching {

            val alarm: Alarm = _preAlarmWithDate.value!!.alarm
            var alarmDate: List<AlarmDate> = _preAlarmWithDate.value!!.alarmDate

            alarm.run {
                title = alarmTitle.value
                isRepeat = !flowSelectedDays.value.filter { it }.isNullOrEmpty()
                onOff = true
            }

            if (alarm.isRepeat) {

                val currentAlarmDate = mutableListOf<AlarmDate>()

                flowSelectedDays.value.forEachIndexed { index, selectDay ->

                    if (selectDay) {
                        val date = Calendar.getInstance().apply {
                            set(Calendar.YEAR, _selectedYear.value)
                            set(Calendar.MONTH, _selectedMonth.value - 1)
                            set(Calendar.DAY_OF_WEEK, index + 1)
                            set(Calendar.HOUR_OF_DAY, selectedHour.value)
                            set(Calendar.MINUTE, selectedMinute.value)
                        }
                        alarmDate.find {
                            Calendar.getInstance().apply {
                                time = it.date
                            }.get(Calendar.DAY_OF_WEEK) - 1 == index
                        }?.let {

                            currentAlarmDate.add(
                                AlarmDate(
                                    it.index, it.alarmIndex, date.time
                                )
                            )

                        } ?: run {
                            currentAlarmDate.add(
                                AlarmDate(
                                    alarmIndex = alarm.index,
                                    date = date.time
                                )
                            )
                        }
                    }

                }

                Timber.d("before DateList => ${_preAlarmWithDate.value!!.alarmDate}")
                Timber.d("current DateList => $currentAlarmDate")
                Timber.d("equals? -> ${_preAlarmWithDate.value!!.alarmDate == currentAlarmDate}")

                alarmDate = currentAlarmDate
            }else{

                 alarmDate[0].date = Calendar.getInstance().apply {
                     set(Calendar.YEAR, _selectedYear.value)
                     set(Calendar.MONTH, _selectedMonth.value - 1)
                     set(Calendar.DAY_OF_MONTH, _selectedDate.value)
                     set(Calendar.HOUR_OF_DAY, selectedHour.value)
                     set(Calendar.MINUTE, selectedMinute.value)
                 }.time

            }

            if (_preAlarmWithDate.value!!.alarm != alarm) {
                Timber.d("update alarm")
                updateUseCase.updateAlarmInfo(alarm)
            }

            if (_preAlarmWithDate.value!!.alarmDate != alarmDate) {
                Timber.d("update alarmDate")
                insertUseCase.insertAlarmDate(alarmDate)
            }

            AlarmWithDate(alarm, alarmDate)
        }.onSuccess {
            success(it)
        }.onFailure {
            failure(it)
        }
    }

    private suspend fun insert(
        success: (alarmWithDate: AlarmWithDate) -> Unit,
        failure: (t: Throwable) -> Unit
    ) =
        viewModelScope.launch {
            runCatching {

                var randomNumber: Int
                while (true) {
                    randomNumber = createRandomNumber()
                    if (!_randomPendingNumber.value.contains(randomNumber))
                        break
                }

                val alarm: Alarm
                val dateList: List<AlarmDate>

                if (flowSelectedDays.value.filter { it }.isNullOrEmpty()) {

                    alarm = Alarm(
                        pendingId = randomNumber,
                        title = alarmTitle.value,
                        isRepeat = false,
                        onOff = true
                    )

                    dateList = listOf(
                        AlarmDate(date = Calendar.getInstance().apply {
                            set(Calendar.YEAR, _selectedYear.value)
                            set(Calendar.MONTH, _selectedMonth.value - 1)
                            set(Calendar.DAY_OF_MONTH, _selectedDate.value)
                            set(Calendar.HOUR_OF_DAY, selectedHour.value)
                            set(Calendar.MINUTE, selectedMinute.value)
                        }.time)
                    )

                    insertUseCase.insertAlarm(alarm, dateList)

                } else {

                    alarm = Alarm(
                        pendingId = randomNumber,
                        title = alarmTitle.value,
                        isRepeat = true,
                        onOff = true
                    )

                    dateList = mutableListOf<AlarmDate>()

                    flowSelectedDays.value.forEachIndexed { index, boolean ->

                        if (boolean) {
                            val date = Calendar.getInstance().apply {
                                set(Calendar.YEAR, _selectedYear.value)
                                set(Calendar.MONTH, _selectedMonth.value - 1)
                                set(Calendar.DAY_OF_WEEK, index + 1)
                                set(Calendar.HOUR_OF_DAY, selectedHour.value)
                                set(Calendar.MINUTE, selectedMinute.value)
                            }

                            if (date.time <= Date())
                                date.add(Calendar.DAY_OF_MONTH, 7)

                            dateList.add(AlarmDate(date = date.time))
                        }
                    }

                    insertUseCase.insertAlarm(alarm, dateList)

                }

                AlarmWithDate(alarm, dateList)
            }.onSuccess {
                success(it)
            }.onFailure {
                failure(it)
            }
        }


}