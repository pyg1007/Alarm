package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kr.ryan.alarm.data.Alarm
import java.text.SimpleDateFormat
import java.util.*

/**
 * Alarm
 * Class: AlarmViewModel
 * Created by pyg10.
 * Created On 2021-08-04.
 * Description:
 */
class AlarmViewModel : ViewModel() {

    private val _alarmList = MutableLiveData<List<Alarm>>()
    val alarmStatus:LiveData<String> = Transformations.map(_alarmList){

        runCatching {
            val current = Date()
            val fastAlarm = it.filter { alarm -> alarm.alarmOnOff || current.after(alarm.alarmTime) }.minByOrNull { alarm -> alarm.alarmTime }
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            simpleDateFormat.format(fastAlarm)
        }.onFailure {
            Log.e("Transformations Error", "Exception -> ${it.message}")
        }.getOrDefault("Empty")

    }


}