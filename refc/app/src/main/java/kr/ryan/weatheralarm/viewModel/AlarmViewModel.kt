package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmViewModel
 * Created by pyg10.
 * Created On 2021-10-23.
 * Description:
 */
@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val selectUseCase: AlarmSelectUseCase,
    private val deleteUseCase: AlarmDeleteUseCase,
    private val insertUseCase: AlarmInsertUseCase
) : ViewModel() {

    private val _alarmList = MutableStateFlow(listOf<Alarm>())
    val alarmList
        get() = _alarmList.asStateFlow()

    private val _alarmDateList = MutableStateFlow(listOf<AlarmDate>())
    val alarmDateList = _alarmDateList.asStateFlow()

    private val _relation = MutableStateFlow(listOf<AlarmWithDate>())
    val alarmRelation = _relation.asStateFlow()

    private val _onClickAdd = MutableStateFlow(false)
    val onClickAdd
        get() = _onClickAdd.asStateFlow()

    private val _onClickMore = MutableStateFlow(false)
    val onClickMore
        get() = _onClickMore.asStateFlow()

    init {
        observeAlarmList()
        observeAlarmDateList()
        observeAlarmWithDate()
    }

    private fun observeAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarm().collect {
            _alarmList.emit(it)
        }
    }

    private fun observeAlarmDateList() = viewModelScope.launch {
        selectUseCase.selectAlarmDate().collect {
            _alarmDateList.emit(it)
        }
    }

    private fun observeAlarmWithDate() = viewModelScope.launch {
        selectUseCase.selectAlarmWithDate().collect {
            _relation.emit(it)
        }
    }

    fun activeAddBtn() = viewModelScope.launch {
        _onClickAdd.emit(true)
    }

    fun activeMoreBtn() = viewModelScope.launch {
        _onClickMore.emit(true)
    }

    fun initAddState() = viewModelScope.launch {
        _onClickAdd.emit(false)
    }

    fun initMoreState() = viewModelScope.launch {
        _onClickMore.emit(false)
    }

    private fun insertAlarm() = viewModelScope.launch {
        val alarm = Alarm(title = "ABCD", onOff = true)
        insertUseCase.insertAlarm(alarm)
    }

    private fun insertAlarmDate() = viewModelScope.launch {
        val alarmDate = AlarmDate(alarmId = 3, date = Date())
        insertUseCase.insertAlarmDate(alarmDate)
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

}