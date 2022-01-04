package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.data.AlarmStatus
import kr.ryan.weatheralarm.data.AlarmWithDate
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * WeatherAlarm
 * Class: TimeAdapter
 * Created by pyg10.
 * Created On 2021-11-04.
 * Description:
 */
object TimeAdapter {

    @JvmStatic
    @BindingAdapter("Meridiem")
    fun TextView.convertTimeToMeridiem(status: AlarmStatus) {
        text = when (status) {
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("a", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                SimpleDateFormat("a", Locale.getDefault()).format(status.date[0])
            }
        }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun TextView.convertTime(status: AlarmStatus) {
        text = when (status) {
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("hh : mm", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                SimpleDateFormat("hh : mm", Locale.getDefault()).format(status.date[0])
            }
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun TextView.convertDate(status: AlarmStatus) {
        text = when (status) {
            is AlarmStatus.DateAlarm -> {
                SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale.getDefault()).format(status.date)
            }
            is AlarmStatus.DaysAlarm -> {
                null
            }
        }
    }

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