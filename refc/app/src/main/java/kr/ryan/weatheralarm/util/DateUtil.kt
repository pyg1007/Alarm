package kr.ryan.weatheralarm.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: DateUtil
 * Created by pyg10.
 * Created On 2021-12-22.
 * Description:
 */

fun Date.convertDateString(): String {
    val simpleDateFormat = SimpleDateFormat("MM월 dd일 (E)", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Date.getCurrentYear(): Int {
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentMonth(): Int {
    val simpleDateFormat = SimpleDateFormat("MM", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentDate(): Int {
    val simpleDateFormat = SimpleDateFormat("dd", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentHour(): Int {
    val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun Date.getCurrentMin(): Int {
    val simpleDateFormat = SimpleDateFormat("mm", Locale.getDefault())
    return simpleDateFormat.format(this).toInt()
}

fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(
    combine(flow1, flow2, flow3, ::Triple),
    combine(flow4, flow5, flow6, ::Triple)
) { t1, t2 ->
        transform(
            t1.first,
            t1.second,
            t1.third,
            t2.first,
            t2.second,
            t2.third
        )
}