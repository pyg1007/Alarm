package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.domain.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.domain.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.domain.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.domain.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.AlarmEvent
import kr.ryan.weatheralarm.util.Route
import kr.ryan.weatheralarm.util.UiEvent
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
    private val deleteUseCase: AlarmDeleteUseCase,
    private val updateUseCase: AlarmUpdateUseCase
) : ViewModel() {

    private val _alarmList = MutableStateFlow<List<AlarmWithDate>>(emptyList())
    val alarmList = _alarmList.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var deleteAlarmDate: AlarmWithDate? = null

    init {
        selectAlarmList()
    }

    private fun selectAlarmList() = viewModelScope.launch {
        selectUseCase<Flow<List<AlarmWithDate>>>("Select", null, null).collect {
            _alarmList.emit(it)
        }
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

            is AlarmEvent.OnUpdate -> {
                updateAlarm(event.alarmWithDate)
            }

            is AlarmEvent.OnAllDeleteClick -> {
                allDeleteAlarm(*event.alarm.toTypedArray())
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
            "MORE" -> _uiEvent.emit(UiEvent.Navigate(Route.MORE))
            "CALENDAR" -> _uiEvent.emit(UiEvent.Navigate(Route.CALENDAR))
            else -> throw IllegalStateException("알 수 없는 루트")
        }
    }

    private fun allDeleteAlarm(vararg alarm: Alarm) = viewModelScope.launch {
        deleteUseCase("Multi", *alarm)
    }

    private fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase("Single", alarm)
    }

    private fun insertAlarm(alarmWithDate: AlarmWithDate) = viewModelScope.launch {
        insertUseCase("Single", alarmWithDate.alarm, alarmWithDate.alarmDate)
    }

    private fun updateAlarm(alarmWithDate: AlarmWithDate) = viewModelScope.launch {
        updateUseCase("Single", alarmWithDate.alarm, null)
    }

    fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}