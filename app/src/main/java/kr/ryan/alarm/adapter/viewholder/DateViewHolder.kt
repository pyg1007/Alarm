package kr.ryan.alarm.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.alarm.R
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.databinding.RecyclerDateBinding
import kr.ryan.alarm.utility.dateToString

/**
 * Alarm
 * Class: DateViewHolder
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
class DateViewHolder private constructor(private val binding: RecyclerDateBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(alarm: Alarm){

        binding.apply {
            alarmDate = alarm.alarmTimeList[0].dateToString("MM월 dd일 (E)")
            alarmStatus = alarm.alarmOnOff
        }

        binding.alarmTime.apply {
            title = alarm.title ?: ""
            alarm.alarmTimeList[0].also {
                meridiem = it.dateToString("a")
                time = it.dateToString("hh:mm")
            }
        }

        binding.root.setOnClickListener {
            itemClickEvent?.let{event ->
                event(alarm)
            }

        }

        binding.root.setOnLongClickListener {
            itemLongClickEvent?.let { event->
                event(alarm)
                true
            }
            false

        }

    }

    companion object{

        private var itemClickEvent: ((Alarm) -> Unit)? = null
        private var itemLongClickEvent: ((Alarm) -> Unit)? = null

        fun from(parent: ViewGroup, onItemClickEventListener: ((Alarm) -> Unit)?, onItemLongClickEventListener: ((Alarm) -> Unit)?) : DateViewHolder{
            itemClickEvent = onItemClickEventListener
            itemLongClickEvent = onItemLongClickEventListener
            return DateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_date, parent, false))
        }
    }

}