package kr.ryan.alarm.utility

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kr.ryan.alarm.R

/**
 * Alarm
 * Class: DaySelectedDrawCircle
 * Created by pyg10.
 * Created On 2021-08-15.
 * Description:
 */

fun Context.createDraw(@DrawableRes drawableRes: Int, day: Int): Drawable? {

    return ContextCompat.getDrawable(this, drawableRes)?.let {
        it.mutate()
        DrawableCompat.wrap(it).also { wrapDrawable ->
            when (day) {
                1 -> (wrapDrawable as GradientDrawable).setStroke(8, ContextCompat.getColor(this, R.color.red))
                7 -> (wrapDrawable as GradientDrawable).setStroke(8, ContextCompat.getColor(this, R.color.blue))
                else -> (wrapDrawable as GradientDrawable).setStroke(8, ContextCompat.getColor(this, R.color.black))
            }
        }
    }


}