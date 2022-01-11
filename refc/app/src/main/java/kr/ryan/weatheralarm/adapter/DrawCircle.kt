package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.Job
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
    fun TextView.setDrawCircle(result: (Int) -> Job){
        setOnClickListener {
            if (background == null) {
                when (id) {
                    R.id.tv_sunday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_red)
                        result(0)
                    }
                    R.id.tv_monday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                        result(1)
                    }
                    R.id.tv_tuesday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                        result(2)
                    }
                    R.id.tv_wednesday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                        result(3)
                    }
                    R.id.tv_thursday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                        result(4)
                    }
                    R.id.tv_friday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_black)
                        result(5)
                    }
                    R.id.tv_saturday -> {
                        background = ContextCompat.getDrawable(context, R.drawable.circle_date_blue)
                        result(6)
                    }
                }
            }else{
                when(id){
                    R.id.tv_sunday -> {
                        background = null
                        result(0)
                    }
                    R.id.tv_monday -> {
                        background = null
                        result(1)
                    }
                    R.id.tv_tuesday -> {
                        background = null
                        result(2)
                    }
                    R.id.tv_wednesday -> {
                        background = null
                        result(3)
                    }
                    R.id.tv_thursday -> {
                        background = null
                        result(4)
                    }
                    R.id.tv_friday -> {
                        background = null
                        result(5)
                    }
                    R.id.tv_saturday -> {
                        background = null
                        result(6)
                    }
                }
            }
        }
    }


}