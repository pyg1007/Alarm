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

    init {
        observeAlarmList()
    }

    private fun observeAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarm().collect {
            _alarmList.emit(it)
        }
    }

    private fun mockData() = viewModelScope.launch {

        val calendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, 11)
            set(Calendar.DAY_OF_MONTH, 9)
        }

        Timber.d(
            "${
                insertUseCase.insertAlarm(
                    Alarm(
                        0,
                        "알람 1",
                        calendar.timeInMillis,
                        listOf(calendar.time),
                        true
                    )
                )
            }"
        )
    }


    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

}