package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.AlarmStatus
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.utility.fastAlarm
import java.text.SimpleDateFormat
import java.util.*

/**
 * Alarm
 * Class: AlarmViewModel
 * Created by pyg10.
 * Created On 2021-08-04.
 * Description:
 */
class AlarmViewModel(private val repository: AlarmRepository) : ViewModel() {

    private val _alarmList = repository.alarmList.asLiveData()
    val alarmList: LiveData<List<Alarm>>
        get() = _alarmList

    val alarmStatus:LiveData<String> = Transformations.map(_alarmList){

        runCatching {
            val current = Date()
            val alarmDate = it.filter { alarm -> alarm.alarmOnOff || current.after(alarm.alarmTime) || alarm.alarmStatus == AlarmStatus.DATE }.minByOrNull { alarm -> alarm.alarmTime }
            val alarmDay = it.filter { alarm -> alarm.alarmOnOff }
            alarmDate?.alarmTime!!.fastAlarm()
        }.onFailure {
            Log.e("Transformations Error", "Exception -> ${it.message}")
        }.getOrDefault("Empty")

    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAlarm(alarm)
    }

}