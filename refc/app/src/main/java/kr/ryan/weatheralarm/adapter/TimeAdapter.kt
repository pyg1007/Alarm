package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.R
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
    fun TextView.convertTimeToMeridiem(dayList: List<Date>) {
        var date = Date()
        if (dayList.size == 1) date = dayList.sorted()[0]
        else {
            val currentTime = Calendar.getInstance().time
            dayList.sorted().forEach {
                if (currentTime < it) {
                    date = it
                    return
                }
            }
        }

        text = SimpleDateFormat("a", Locale.getDefault()).format(date)
    }

    @JvmStatic
    @BindingAdapter("time")
    fun TextView.convertTime(dayList: List<Date>) {
        var date = Date()
        if (dayList.size == 1) date = dayList.sorted()[0]
        else {
            val currentTime = Calendar.getInstance().time
            dayList.sorted().forEach {
                if (currentTime < it) {
                    date = it
                    return
                }
            }
        }

        text = SimpleDateFormat("hh : mm", Locale.getDefault()).format(date)
    }

    @JvmStatic
    @BindingAdapter("date")
    fun TextView.convertDate(dayList: List<Date>) {
        var date = Date()
        if (dayList.size == 1) date = dayList.sorted()[0]
        else {
            val currentTime = Calendar.getInstance().time
            dayList.sorted().forEach {
                if (currentTime < it) {
                    date = it
                    return
                }
            }
        }

        text = SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.getDefault()).format(date)
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