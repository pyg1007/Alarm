package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import kr.ryan.weatheralarm.R

/**
 * WeatherAlarm
 * Class: DrawCircle
 * Created by pyg10.
 * Created On 2021-12-20.
 * Description:
 */
object DrawCircle {

    @BindingAdapter("draw")
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

    @BindingAdapter("drawAttrChanged")
    @JvmStatic
    fun TextView.changeListener(inverseBindingListener: InverseBindingListener) {
        inverseBindingListener.onChange()
    }

    @InverseBindingAdapter(attribute = "draw", event = "drawAttrChanged")
    @JvmStatic
    fun TextView.getDrawCircle(): Boolean {
        return background != null
    }

}