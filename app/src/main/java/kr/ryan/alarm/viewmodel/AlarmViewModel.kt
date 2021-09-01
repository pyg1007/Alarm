package kr.ryan.alarm.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.repository.AlarmRepository
import kr.ryan.alarm.utility.fastAlarm
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

    val alarmStatus: LiveData<String> = Transformations.map(_alarmList) {

        runCatching {
            val multipleAlarmDate =
                it.filter { alarm -> alarm.alarmOnOff } // 켜저있는 알람만 필터
                    .map { alarm -> // 리스트화
                        alarm.alarmTimeList.filter { date -> Date() <= date }
                            .minOrNull() // 등록되어있는 시간이 현재시간보다 같거나 큰경우만 필터링한 후 가장작은 값을 추출
                    }.minByOrNull { date -> date!! }!! // 추출된 값중에서 가장 작은것을 지정

            multipleAlarmDate.fastAlarm() // 시간 스트링으로 변환

        }.onFailure {
            Log.e("Transformations Error", "Exception -> ${it.message}")
        }.getOrDefault("등록되어있는 알람이 없습니다.")

    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.deleteAlarm(alarm)
    }

}