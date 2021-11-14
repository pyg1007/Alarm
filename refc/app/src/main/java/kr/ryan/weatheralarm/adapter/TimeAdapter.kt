package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.AlarmStatus
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
                val currentDate = Calendar.getInstance().time
                SimpleDateFormat("a", Locale.getDefault()).format(status.date.sorted().find { it >= currentDate } ?: "")
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
                val currentDate = Calendar.getInstance().time
                SimpleDateFormat("hh : mm", Locale.getDefault()).format(status.date.sorted().find { it >= currentDate } ?: "")
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
                val currentDate = Calendar.getInstance().time
                SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.getDefault()).format(status.date.sorted().find { it >= currentDate } ?: "")
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["day", "dayList"], requireAll = true)
    fun TextView.drawCircle(day: Int, dayList: List<Date>) {
        dayList.find { Calendar.getInstance().apply { time = it }.get(Calendar.DAY_OF_WEEK) == day }
            ?.let {
                background =
                    ContextCompat.getDrawable(rootView.context, R.drawable.ic_launcher_background)
            } ?: run {
            background = null
        }
    }

}