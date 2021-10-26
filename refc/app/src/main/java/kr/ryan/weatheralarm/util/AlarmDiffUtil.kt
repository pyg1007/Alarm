package kr.ryan.weatheralarm.util

import androidx.recyclerview.widget.DiffUtil
import kr.ryan.weatheralarm.data.Alarm

/**
 * WeatherAlarm
 * Class: AlarmDiffUtil
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {

    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean = oldItem.index == newItem.index
}