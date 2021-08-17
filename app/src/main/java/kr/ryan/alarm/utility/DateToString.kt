package kr.ryan.alarm.utility

import java.text.SimpleDateFormat
import java.util.*

/**
 * Alarm
 * Class: DateToString
 * Created by pyg10.
 * Created On 2021-08-17.
 * Description:
 */

fun Date.dateToString(format: String, locale: Locale = Locale.getDefault()): String{
    val simpleDateFormat = SimpleDateFormat(format, locale)
    return simpleDateFormat.format(this)
}