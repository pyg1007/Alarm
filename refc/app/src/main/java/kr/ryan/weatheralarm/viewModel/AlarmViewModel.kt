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
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import timber.log.Timber
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
    private val deleteUseCase: AlarmDeleteUseCase
) : BaseAlarmViewModel(selectUseCase) {

    val alarmList = selectAllAlarmList.asLiveData()

    private val _onClickAdd = MutableStateFlow(false)
    val onClickAdd
        get() = _onClickAdd.asStateFlow()

    private val _onClickMore = MutableStateFlow(false)
    val onClickMore
        get() = _onClickMore.asStateFlow()

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

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

}