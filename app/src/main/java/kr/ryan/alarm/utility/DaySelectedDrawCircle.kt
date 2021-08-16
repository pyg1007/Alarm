package kr.ryan.alarm.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
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

fun Context.createDraw(@DrawableRes drawableRes: Int, day: String): Drawable?{

    return ContextCompat.getDrawable(this, drawableRes)?.let {
        it.mutate()
        DrawableCompat.wrap(it).also { wrapDrawable->
            if (day == "일")
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.red))
            else if (day == "토")
                DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.blue))
        }
    }



}