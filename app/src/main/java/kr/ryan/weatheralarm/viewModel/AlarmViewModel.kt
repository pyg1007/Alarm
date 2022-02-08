package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.util.*
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
    private val insertUseCase: AlarmInsertUseCase,
    private val deleteUseCase: AlarmDeleteUseCase
) : ViewModel() {

    private var timeJob: Job? = null

    private val _alarmList = MutableStateFlow<List<AlarmWithDate>>(emptyList())
    val alarmList = _alarmList.asStateFlow()

    private val _remainTime = MutableStateFlow("")
    val remainTime = _remainTime.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var deleteAlarmDate: AlarmWithDate? = null

    init {
        selectAlarmList()
        timeLoop()
    }

    private fun selectAlarmList() = viewModelScope.launch {
        selectUseCase.selectAlarmList().collect {
            _alarmList.emit(it)
        }
    }

    fun startTimeLoopJob(){
        timeJob?.let {
            if (!it.isActive)
                it.start()
        }
    }

    private fun timeLoop(){
        timeJob = viewModelScope.launch {
            Timber.d("startLoop")
            while (true){
                val currentTime = Date()
                if ((currentTime.time / 1000) % 60 == 0L) {
                    _remainTime.emit(_alarmList.value.findFastAlarmDate()?: "")
                }
                delay(1000L)
            }
        }
    }

    fun cancelTimeLoopJob(){
        timeJob?.cancel()
    }

    fun onEvent(event: AlarmEvent) {
        when (event) {
            is AlarmEvent.OnAddClick -> { // 추가버튼
                sendEvent(UiEvent.Navigate(Route.ADD_MODE))
            }
            is AlarmEvent.OnUndoDeleteClick -> { // 스낵바 취소버튼
                deleteAlarmDate?.let {
                    insertAlarm(it)
                }
            }
            is AlarmEvent.OnDeleteClick -> { // 롱클릭시 나타나는 팝업메뉴 클릭시
                deleteAlarmDate = event.alarmWithDate
                deleteAlarm(event.alarmWithDate.alarm)
                sendEvent(
                    UiEvent.ShowSnackBar(
                        "${event.alarmWithDate.alarm.title} 을 삭제하였습니다.",
                        "실행 취소"
                    )
                )
            }
            is AlarmEvent.OnAlarmClick -> { // 설정되어있는 알람 클릭
                sendEvent(UiEvent.Navigate(Route.EDIT_MODE, event.alarmWithDate))
            }
        }
    }

    fun onClickBtn(route: String) = viewModelScope.launch {
        when (route) {
            "ADD" -> _uiEvent.emit(UiEvent.Navigate(Route.ADD_MODE))
            "CANCEL" -> _uiEvent.emit(UiEvent.Navigate(Route.CANCEL))
            "SAVE" -> _uiEvent.emit(UiEvent.Navigate(Route.SAVE))
            else -> _uiEvent.emit(UiEvent.ShowSnackBar("${route}를 클릭하셨습니다."))
        }
    }

    private fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

    private fun insertAlarm(alarmWithDate: AlarmWithDate) = viewModelScope.launch {
        insertUseCase.insertAlarm(alarmWithDate.alarm, alarmWithDate.alarmDate)
    }

    fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}