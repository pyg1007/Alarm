package kr.ryan.weatheralarm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmDeleteUseCase
import kr.ryan.weatheralarm.usecase.AlarmInsertUseCase
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.util.AlarmEvent
import kr.ryan.weatheralarm.util.Route
import kr.ryan.weatheralarm.util.UiEvent
import timber.log.Timber
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

    val alarmList = flow {
        selectUseCase.selectAlarmList().collect {
            emit(it)
        }
    }.catch {e ->
        Timber.d("Exception $e")
    }.asLiveData()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var deleteAlarmDate: AlarmWithDate? = null

    fun onEvent(event: AlarmEvent) {
        when (event) {
            is AlarmEvent.OnAddClick -> { // 추가버튼
                sendChannelEvent(UiEvent.Navigate(Route.ADD_MODE))
            }
            is AlarmEvent.OnUndoDeleteClick -> { // 스낵바 취소버튼
                deleteAlarmDate?.let {
                    insertAlarm(it)
                }
            }
            is AlarmEvent.OnDeleteClick -> { // 롱클릭시 나타나는 팝업메뉴 클릭시
                deleteAlarmDate = event.alarmWithDate
                deleteAlarm(event.alarmWithDate.alarm)
                sendChannelEvent(
                    UiEvent.ShowSnackBar(
                        "${event.alarmWithDate.alarm.title} 을 삭제하였습니다.",
                        "실행 취소"
                    )
                )
            }
            is AlarmEvent.OnAlarmClick -> { // 설정되어있는 알람 클릭
                sendChannelEvent(UiEvent.Navigate(Route.EDIT_MODE, event.alarmWithDate))
            }
        }
    }

    fun onClickBtn(route: String) = viewModelScope.launch {
        when (route) {
            "ADD" -> _uiEvent.send(UiEvent.Navigate(Route.ADD_MODE))
            "CANCEL" -> _uiEvent.send(UiEvent.Navigate(Route.CANCEL))
            "SAVE" -> _uiEvent.send(UiEvent.Navigate(Route.SAVE))
            else -> _uiEvent.send(UiEvent.ShowSnackBar("${route}를 클릭하셨습니다."))
        }
    }

    private fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        deleteUseCase.deleteAlarm(alarm)
    }

    private fun insertAlarm(alarmWithDate: AlarmWithDate) = viewModelScope.launch {
        insertUseCase.insertAlarm(alarmWithDate.alarm, alarmWithDate.alarmDate)
    }

    fun sendChannelEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}