package kr.ryan.weatheralarm.util

/**
 * WeatherAlarm
 * Class: UiEvent
 * Created by pyg10.
 * Created On 2022-01-05.
 * Description:
 */
sealed class UiEvent{

    object PopUpStack: UiEvent()

    data class Navigate(val route: String) : UiEvent()

    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ) : UiEvent()
}
