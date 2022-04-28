package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.domain.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.domain.usecase.AlarmUpdateUseCase
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
    private val selectUseCase: AlarmSelectUseCase,
    private val updateUseCase: AlarmUpdateUseCase
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
        selectUseCase<Flow<List<AlarmWithDate>>>("Select", null, null).collect {
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
                    if (alarmList.value.isNullOrEmpty())
                        _remainTime.emit("등록되어있는 알람이 없습니다.")
                    else
                        _remainTime.emit(alarmList.value.findFastAlarmDate() ?: "켜져있는 알람이 없습니다.")
                } else {
                    if ((currentDate / 1000) % 60 == 0L) {
                        if (alarmList.value.isNullOrEmpty())
                            _remainTime.emit("등록되어있는 알람이 없습니다.")
                        else
                            _remainTime.emit(alarmList.value.findFastAlarmDate() ?: "켜져있는 알람이 없습니다.")
                    }
                }
                fixAlarmDate(currentDate)
                delay(timeMillis = 1000)
            }
        }
    }

    private suspend fun fixAlarmDate(currentTime: Long){
        if (alarmList.value.isNullOrEmpty())
            return

        // 꺼져있는 알람
        val offAlarmList = alarmList.value.filter { alarmWithDate -> !alarmWithDate.alarm.onOff}

        offAlarmList.forEach { alarmWithDate ->
            alarmWithDate.alarmDate.forEach {alarmDate ->
                if (alarmWithDate.alarm.isRepeat){
                    if (alarmDate.date.time < currentTime){
                        val calendar = Calendar.getInstance().apply {
                            time = alarmDate.date
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, 7)
                        alarmDate.date = calendar.time
                    }
                }else{
                    if (alarmDate.date.time < currentTime){
                        val calendar = Calendar.getInstance().apply {
                            time = alarmDate.date
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                        alarmDate.date = calendar.time
                    }
                }
            }

            updateUseCase("UpdateAlarmDate",null ,alarmWithDate.alarmDate)
        }
    }

}