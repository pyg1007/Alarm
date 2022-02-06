package kr.ryan.weatheralarm.util

import android.app.Activity
import androidx.fragment.app.DialogFragment
import androidx.window.layout.WindowMetricsCalculator

/**
 * WeatherAlarm
 * Class: DialogResizeUtil
 * Created by pyg10.
 * Created On 2021-12-10.
 * Description:
 */

fun Activity.dialogFragmentResize(dialogFragment: DialogFragment, width: Float, height: Float) {
    val size = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
    val window = dialogFragment.dialog?.window

    val customWidth = (size.bounds.width() * width).toInt()
    val customHeight = (size.bounds.height() * height).toInt()
    window?.setLayout(customWidth, customHeight)
}