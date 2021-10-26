package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

    private val _alarmList = MutableStateFlow(listOf<Alarm>())
    val alarmList = _alarmList.asStateFlow()

    init {
        selectAlarm()
    }

    private fun selectAlarm() = viewModelScope.launch {
        selectUseCase.selectAlarm().collect {
            _alarmList.emit(it)
        }
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch{
        deleteUseCase.deleteAlarm(alarm)
    }

}