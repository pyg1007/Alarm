package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.data.AlarmWithDate
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: TimeAdapter
 * Created by pyg10.
 * Created On 2021-11-04.
 * Description:
 */
object TimeAdapter {

    @JvmStatic
    @BindingAdapter("setFastTime")
    fun TextView.findFastTime(time: List<AlarmWithDate>?) {
        text = null

        val alarmOnList = time?.filter { it.alarm.onOff }
        val remainTimeAlarmList = mutableListOf<AlarmWithDate>()

        alarmOnList?.forEach {
            run {


            }
        }


        Timber.d("Init Time -> $time")
        Timber.d("First Filter -> ${time?.filter { it.alarm.onOff }}")
        Timber.d(
            "Second Sort -> ${
                time?.filter { it.alarm.onOff }
                    ?.sortedBy { alarmWithDate -> alarmWithDate.alarmDate.sortedBy { it.date }[0].date }
            }"
        )
    }

}