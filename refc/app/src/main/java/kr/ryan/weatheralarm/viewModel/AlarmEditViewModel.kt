package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmEditViewModel
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
@HiltViewModel
class AlarmEditViewModel @Inject constructor(
    private val insertUseCase: AlarmInsertUseCase,
    private val updateUseCase: AlarmUpdateUseCase
) : ViewModel(){

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode = _isEditMode.asStateFlow()

    private val _alarm = MutableStateFlow<Alarm?>(null)

    val alarmTitle = _alarm.transform<Alarm?, String> {
        it?.title
    }

    val alarmTime = _alarm.transform<Alarm?, String> {
        it?.time?.let {time ->
            SimpleDateFormat("HH : mm", Locale.getDefault()).format(Date(time))
        }
    }

    fun changeAlarm(alarm: Alarm) = viewModelScope.launch {
        _alarm.emit(alarm)
    }

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch {
        insertUseCase.insertAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch {
        updateUseCase.updateAlarm(alarm)
    }

}