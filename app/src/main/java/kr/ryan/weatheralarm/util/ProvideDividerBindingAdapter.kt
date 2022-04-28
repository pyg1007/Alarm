package kr.ryan.weatheralarm.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * WeatherAlarm
 * Class: ProvideDividerBindingAdapter
 * Created by pyg10.
 * Created On 2022-04-27.
 * Description:
 */
object ProvideDividerBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["dividerHeight", "dividerPadding", "dividerColor"], requireAll = false)
    fun RecyclerView.provideDivider(
        dividerHeight: Float?,
        dividerPadding: Float?,
        @ColorInt dividerColor: Int?
    ) {
        val divider = CustomDividerItemDecoration(
            height = dividerHeight ?: 0f,
            padding = dividerPadding ?: 0f,
            color = dividerColor ?: Color.TRANSPARENT
        )

        addItemDecoration(divider)
    }

}