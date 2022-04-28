package kr.ryan.weatheralarm.util

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import kr.ryan.weatheralarm.R

/**
 * WeatherAlarm
 * Class: DrawCircleBindingAdapter
 * Created by pyg10.
 * Created On 2022-04-27.
 * Description:
 */
object DrawCircleBindingAdapter {

    @BindingAdapter("drawCirCle")
    @JvmStatic
    fun TextView.setDrawCircle(value: Boolean) {
        if (background == null && value) {
            background = when (id) {
                R.id.tv_sunday ->
                    ContextCompat.getDrawable(context, R.drawable.circle_date_red)
                R.id.tv_saturday ->
                    ContextCompat.getDrawable(context, R.drawable.circle_date_blue)
                else ->
                    ContextCompat.getDrawable(context, R.drawable.circle_date_black)
            }

        } else if (background != null && !value) {
            background = null
        }
    }

    @BindingAdapter("drawCircleAttrChanged")
    @JvmStatic
    fun TextView.changeListener(inverseBindingListener: InverseBindingListener) {
        inverseBindingListener.onChange()
    }

    @InverseBindingAdapter(attribute = "drawCirCle", event = "drawCircleAttrChanged")
    @JvmStatic
    fun TextView.getDrawCircle(): Boolean {
        return background != null
    }

}