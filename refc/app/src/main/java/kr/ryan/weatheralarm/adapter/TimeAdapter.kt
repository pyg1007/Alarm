package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.AlarmStatus
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: TimeAdapter
 * Created by pyg10.
 * Created On 2021-11-04.
 * Description:
 */
object TimeAdapter {

    @JvmStatic
    @BindingAdapter("Meridiem")
    fun TextView.convertTimeToMeridiem(status: AlarmStatus) {
        text = when(status){
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("a", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                SimpleDateFormat("a", Locale.getDefault()).format(status.date[0])
            }
        }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun TextView.convertTime(status: AlarmStatus) {
        text = when(status){
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("hh : mm", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                SimpleDateFormat("hh : mm", Locale.getDefault()).format(status.date[0])
            }
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun TextView.convertDate(status: AlarmStatus) {
        text = when(status){
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                null
            }
        }
    }

}