package kr.ryan.weatheralarm.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.util.convertTime
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
    fun TextView.findFastTime(alarmWithDate: List<AlarmWithDate>?) {


        val alarmOnList = alarmWithDate?.filter{ it.alarm.onOff }

        val filteredDateAlarm = alarmOnList?.filter { !it.alarm.isRepeat }
        val filteredDaysAlarm = alarmOnList?.filter { it.alarm.isRepeat }

        val sortedDateAlarm = filteredDateAlarm?.sortedBy { it.alarmDate[0].date }
        val sortedDaysAlarm = filteredDaysAlarm?.sortedBy { it.alarmDate.sortedBy { alarmDate ->  alarmDate.date }[0].date }

        val fastDate = if (!sortedDateAlarm.isNullOrEmpty())
            sortedDateAlarm[0].alarmDate[0].date
        else
            null

        val fastDays = if (!sortedDaysAlarm.isNullOrEmpty())
            sortedDaysAlarm[0].alarmDate[0].date
        else
            null

        text = if (null == fastDate && null != fastDays){
            fastDays.convertTime()
        }else if (null != fastDate && null == fastDays){
            fastDate.convertTime()
        }else if (null != fastDate && null != fastDays){
            if (fastDate.after(fastDays))
                fastDays.convertTime()
            else
                fastDate.convertTime()
        }else{
            null
        }
    }

}