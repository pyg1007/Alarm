package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
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
    private val insertUseCase: AlarmInsertUseCase // 테스트용
) : ViewModel() {

    private val _alarmList = MutableStateFlow(listOf<Alarm>())
    val alarmList
        get() = _alarmList.asStateFlow()

    private val _onClickAdd = MutableStateFlow(false)
    val onClickAdd
        get() = _onClickAdd.asStateFlow()

    private val _onClickMore = MutableStateFlow(false)
    val onClickMore
        get() = _onClickMore.asStateFlow()

    init {
        observeAlarmList()
    }

    private fun observeAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarm().collect {
            _alarmList.emit(it)
        }
    }

    fun activeAddBtn() = viewModelScope.launch {
        Timber.d("add")
        _onClickAdd.emit(true)
        Timber.d("${onClickAdd.value}")
    }

    fun activeMoreBtn() = viewModelScope.launch {
        Timber.d("more")
        _onClickMore.emit(true)
        Timber.d("${onClickMore.value}")
    }

    fun initAddState() = viewModelScope.launch {
        _onClickAdd.emit(false)
    }

    fun initMoreState() = viewModelScope.launch {
        _onClickMore.emit(false)
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

}