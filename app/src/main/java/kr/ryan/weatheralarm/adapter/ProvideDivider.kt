package kr.ryan.weatheralarm.adapter

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.util.CustomDivider

/**
 * WeatherAlarm
 * Class: ProvideDivider
 * Created by pyg10.
 * Created On 2021-11-03.
 * Description:
 */
object ProvideDivider {

    @JvmStatic
    @BindingAdapter(value = ["dividerHeight", "dividerPadding", "dividerColor"], requireAll = false)
    fun RecyclerView.provideDivider(
        dividerHeight: Float?,
        dividerPadding: Float?,
        @ColorInt dividerColor: Int?
    ) {
        val divider = CustomDivider(
            height = dividerHeight ?: 0f,
            padding = dividerPadding ?: 0f,
            color = dividerColor ?: Color.TRANSPARENT
        )

        addItemDecoration(divider)
    }

}