package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.R
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: DrawCircle
 * Created by pyg10.
 * Created On 2021-12-20.
 * Description:
 */
object DrawCircle {

    @JvmStatic
    @BindingAdapter(value = ["dayIndex", "dayStatus"], requireAll = true)
    fun TextView.drawCircle(index: Int, status: Boolean) {
        Timber.d("$index , $status")
        when (index) {
            0 -> { // 일요일
                background = if (status) ContextCompat.getDrawable(
                    context,
                    R.drawable.circle_date_red
                )
                else null
            }
            6 -> { // 토요일
                background = if (status) ContextCompat.getDrawable(
                    context,
                    R.drawable.circle_date_blue
                )
                else null
            }
            else -> { // 월 ~ 금
                background = if (status) ContextCompat.getDrawable(
                    context,
                    R.drawable.circle_date_black
                )
                else null
            }
        }
    }
}