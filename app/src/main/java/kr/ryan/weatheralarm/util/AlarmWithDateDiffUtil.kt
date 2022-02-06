package kr.ryan.weatheralarm.util

import androidx.recyclerview.widget.DiffUtil
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmWithDate

/**
 * WeatherAlarm
 * Class: AlarmDiffUtil
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class AlarmWithDateDiffUtil : DiffUtil.ItemCallback<AlarmWithDate>() {

    override fun areItemsTheSame(oldItem: AlarmWithDate, newItem: AlarmWithDate): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: AlarmWithDate, newItem: AlarmWithDate): Boolean = oldItem.hashCode() == newItem.hashCode()
}