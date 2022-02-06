package kr.ryan.weatheralarm.util

import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: UiEvent
 * Created by pyg10.
 * Created On 2022-01-05.
 * Description:
 */
sealed class UiEvent{

    object PopUpStack: UiEvent()

    data class Navigate(val route: String, val alarmWithDate: AlarmWithDate? = null) : UiEvent()

    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
}
