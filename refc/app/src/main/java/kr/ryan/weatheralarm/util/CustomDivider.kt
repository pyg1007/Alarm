package kr.ryan.weatheralarm.util

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * WeatherAlarm
 * Class: CustomDivider
 * Created by pyg10.
 * Created On 2021-11-03.
 * Description:
 */
class CustomDivider(private val height: Float, private val padding: Float, @ColorInt private val color: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart + padding
        val right = parent.width - parent.paddingEnd - padding
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            c.drawRect(left, top, right, bottom, paint)
        }
    }
}