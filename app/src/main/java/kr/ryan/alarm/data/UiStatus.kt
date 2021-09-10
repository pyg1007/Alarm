package kr.ryan.alarm.data

/**
 * Alarm
 * Class: Uistatus
 * Created by pyg10.
 * Created On 2021-09-10.
 * Description:
 */
sealed class UiStatus{

    data class Loading(val unit: Unit) : UiStatus()
    data class Complete(val unit: Unit) : UiStatus()

}
