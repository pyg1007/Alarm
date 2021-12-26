package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
    @BindingAdapter("attrDraw")
    fun TextView.setDrawCircle(state: Boolean){
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "attrDraw")
    fun TextView.getDrawCircle(): Boolean{
        return background != null
    }

    @JvmStatic
    @BindingAdapter("attrDrawAttrChanged")
    fun TextView.drawCircle(
        listener : InverseBindingListener
    ){
        setOnClickListener {
            background = if (background == null){
                when (id) {
                    R.id.tv_sunday -> ContextCompat.getDrawable(context, R.drawable.circle_date_red)
                    R.id.tv_saturday -> ContextCompat.getDrawable(context, R.drawable.circle_date_blue)
                    else -> ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                }
            }else{
                null
            }
            listener.onChange()
        }
    }
}