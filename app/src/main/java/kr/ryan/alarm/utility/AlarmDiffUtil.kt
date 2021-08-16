package kr.ryan.alarm.utility

import androidx.recyclerview.widget.DiffUtil
import kr.ryan.alarm.data.Alarm

/**
 * Alarm
 * Class: DiffUtil
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {

    override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
        oldItem.index == newItem.index


    override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean =
        oldItem.hashCode() == newItem.hashCode()

}