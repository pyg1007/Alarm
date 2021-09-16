package kr.ryan.alarm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.repository.AlarmRepository

/**
 * Alarm
 * Class: AlarmUpdateViewModel
 * Created by pyg10.
 * Created On 2021-09-16.
 * Description:
 */
class AlarmUpdateViewModel(private val repository: AlarmRepository): ViewModel() {

    fun onClickUpdateAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.updateAlarm(alarm)
    }

}