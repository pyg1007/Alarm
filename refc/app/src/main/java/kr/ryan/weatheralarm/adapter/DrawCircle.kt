package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.R

/**
 * WeatherAlarm
 * Class: DrawCircle
 * Created by pyg10.
 * Created On 2021-12-20.
 * Description:
 */
object DrawCircle {

    @JvmStatic
    @BindingAdapter("drawCircle")
    fun TextView.setDrawCircle(index: Int, result: (Int) -> Unit){
        background = if (background == null){
            when(index){
                0 -> ContextCompat.getDrawable(context, R.drawable.circle_date_red)
                6 -> ContextCompat.getDrawable(context, R.drawable.circle_date_blue)
                else -> ContextCompat.getDrawable(context, R.drawable.circle_date_black)
            }
        }else null

        result(index)
    }


}