package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.repository.DeleteRepository
import kr.ryan.weatheralarm.repository.InsertRepository
import kr.ryan.weatheralarm.repository.SelectRepository
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
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
) : ViewModel() {

    private val _alarmList = flow<List<Alarm>> {
        selectUseCase.selectAlarm()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    val alarmList
        get() = _alarmList

    init {
        _alarmList
    }


    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch{
        deleteUseCase.deleteAlarm(alarm)
    }

}