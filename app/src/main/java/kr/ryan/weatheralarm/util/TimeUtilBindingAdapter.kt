package kr.ryan.weatheralarm.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: TimeUtilBindingAdapter
 * Created by pyg10.
 * Created On 2022-04-27.
 * Description:
 */
object TimeUtilBindingAdapter {

    @JvmStatic
    @BindingAdapter("setFastTime")
    fun TextView.findFastTime(alarmWithDate: List<AlarmWithDate>?) {

        // alarm 이 켜져있는것만 필터
        val alarmOnList = alarmWithDate?.filter{ it.alarm.onOff }

        // 필터한것중에 요일인지 날짜인지 필터
        val filteredDateAlarm = alarmOnList?.filter { !it.alarm.isRepeat }
        val filteredDaysAlarm = alarmOnList?.filter { it.alarm.isRepeat }

        // 각각을 소트
        val sortedDateAlarm = filteredDateAlarm?.sortedBy { it.alarmDate[0].date }
        val sortedDaysAlarm = filteredDaysAlarm?.sortedBy { it.alarmDate.sortedBy { alarmDate ->  alarmDate.date }[0].date }

        // 가장빠른 날짜
        val fastDate = if (!sortedDateAlarm.isNullOrEmpty())
            sortedDateAlarm[0].alarmDate[0].date
        else
            null

        // 가장빠른 요일
        val fastDays = if (!sortedDaysAlarm.isNullOrEmpty())
            sortedDaysAlarm[0].alarmDate[0].date
        else
            null

        // 요일과 날짜중 빠른것을 스트링으로 변환
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