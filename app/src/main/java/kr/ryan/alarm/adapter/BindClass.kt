package kr.ryan.alarm.adapter

import android.widget.CalendarView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.ryan.alarm.R
import java.util.*

/**
 * Alarm
 * Class: bindAdapter
 * Created by pyg10.
 * Created On 2021-08-17.
 * Description:
 */

object BindClass {

    @JvmStatic
    @BindingAdapter("onDayClick")
    fun onDayClickListener(constraintLayout: ConstraintLayout, result: (Int) -> Unit) {
        constraintLayout.setOnClickListener {
            when (it.id) {
                R.id.const_sunday -> {
                    result(1)
                }
                R.id.const_monday -> {
                    result(2)
                }
                R.id.const_tuesday -> {
                    result(3)
                }
                R.id.const_wednesday -> {
                    result(4)
                }
                R.id.const_thursday -> {
                    result(5)
                }
                R.id.const_friday -> {
                    result(6)
                }
                R.id.const_saturday -> {
                    result(7)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("CalendarDateClick")
    fun onCalendarDateClick(calendarView: CalendarView, result: (Date) -> Unit){
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }.time
            result(date)
        }
    }
}
