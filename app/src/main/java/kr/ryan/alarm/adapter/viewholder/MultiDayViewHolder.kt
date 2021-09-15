package kr.ryan.alarm.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.alarm.R
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.databinding.RecyclerMultiDayBinding
import kr.ryan.alarm.utility.dateToString

/**
 * Alarm
 * Class: MultiDayViewHolder
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
class MultiDayViewHolder private constructor(private val binding: RecyclerMultiDayBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(alarm: Alarm){

        binding.alarmTime.apply {
            title = alarm.title
            meridiem = alarm.alarmTimeList[0].dateToString("a")
            time = alarm.alarmTimeList[0].dateToString("hh:mm")
        }

        binding.root.setOnClickListener {
            itemClickEvent?.let {event->
                event(alarm, it)
            }
        }

        binding.root.setOnLongClickListener {
            itemLongClickEvent?.let {event->
                event(alarm, it)
                true
            }
            false
        }

    }

    companion object{

        private var itemClickEvent: ((Alarm, View) -> Unit)? = null
        private var itemLongClickEvent: ((Alarm, View) -> Unit)? = null

        fun from(parent: ViewGroup, onItemClickEventListener: ((Alarm, View) -> Unit)?, onItemLongClickEventListener: ((Alarm, View) -> Unit)?): MultiDayViewHolder{
            itemClickEvent = onItemClickEventListener
            itemLongClickEvent = onItemLongClickEventListener
            return MultiDayViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_multi_day, parent, false))
        }
    }

}