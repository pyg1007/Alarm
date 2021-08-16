package kr.ryan.alarm.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.ryan.alarm.R
import kr.ryan.alarm.data.Days

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
    fun onDayClickListener(constraintLayout: ConstraintLayout, result: (Days) -> Unit) {
        constraintLayout.setOnClickListener {
            when (it.id) {
                R.id.const_sunday -> {
                    result(Days(1, "일"))
                }
                R.id.const_monday -> {
                    result(Days(2, "월"))
                }
                R.id.const_tuesday -> {
                    result(Days(3, "화"))
                }
                R.id.const_wednesday -> {
                    result(Days(4, "수"))
                }
                R.id.const_thursday -> {
                    result(Days(5, "목"))
                }
                R.id.const_friday -> {
                    result(Days(6, "금"))
                }
                R.id.const_saturday -> {
                    result(Days(7, "토"))
                }
            }
        }
    }
}
