package kr.ryan.weatheralarm.viewModel

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.util.findFastAlarmDate
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: RemainTimerViewModel
 * Created by pyg10.
 * Created On 2022-02-09.
 * Description:
 */
@HiltViewModel
class RemainTimerViewModel @Inject constructor(
    private val selectUseCase: AlarmSelectUseCase
) : ViewModel() {

    private var receiveData = false
    private var flag = false
    private var remainTimerJob: Job? = null

    private val alarmList = MutableStateFlow<List<AlarmWithDate>>(emptyList())

    private val _alarmStatus = MutableStateFlow("")
    val alarmStatus = _alarmStatus.asStateFlow()

    init {
        selectAlarmList()
    }

    private fun selectAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarmList().collect {
            alarmList.emit(it)

            if (!it.filter { alarmWithDate -> alarmWithDate.alarm.onOff }.isNullOrEmpty()) {
                receiveData = true
                remainTimerJob?.let { job ->
                    if (job.isActive) {
                        cancelJob()
                        startJob()
                    }
                } ?: run {
                    startJob()
                }
            } else {
                _alarmStatus.emit("등록된 알람이 없습니다.")
                remainTimerJob?.let { job ->
                    if (job.isActive)
                        cancelJob()
                }
            }
        }
    }

    private fun remainTimer() {
        remainTimerJob = viewModelScope.launch {
            while (true) {
                val currentDate = SystemClock.elapsedRealtime()
                if (!flag) {
                    flag = true
                    _alarmStatus.emit(alarmList.value.findFastAlarmDate() ?: "등록된 알람이 없습니다.")
                } else {
                    if ((currentDate / 1000) % 60 == 0L) {
                        _alarmStatus.emit(alarmList.value.findFastAlarmDate() ?: "등록된 알람이 없습니다.")
                    }
                }
                delay(1000L)
            }
        }
    }

    fun startJob() {
        if (receiveData)
            remainTimer()
    }

    fun cancelJob() {
        remainTimerJob?.cancel()
        remainTimerJob = null
    }

}