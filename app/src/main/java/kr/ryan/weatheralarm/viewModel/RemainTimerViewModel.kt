package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.util.findFastAlarmDate
import java.util.*
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

    private var timerJob: Job? = null

    private var flag = false

    private val _remainTime = MutableStateFlow("등록되어있는 알람이 없습니다.")
    val remainTime = _remainTime.asStateFlow()

    private val alarmList = MutableStateFlow(emptyList<AlarmWithDate>())

    init {
        getAlarmList()
    }

    private fun getAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarmList().collect {
            alarmList.emit(it)

            if (flag)
                flag = false

            start()
        }
    }

    private suspend fun start() {
        timerJob?.cancelAndJoin()
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val currentDate = Date().time
                if (!flag) {
                    flag = true
                    _remainTime.emit(alarmList.value.findFastAlarmDate() ?: "등록되어있는 알람이 없습니다.")
                } else {
                    if ((currentDate / 1000) % 60 == 0L) {
                        withContext(Dispatchers.Main) {
                            _remainTime.emit(
                                alarmList.value.findFastAlarmDate() ?: "등록되어있는 알람이 없습니다."
                            )
                        }
                    }
                }
                delay(timeMillis = 1000)
            }
        }
    }

}