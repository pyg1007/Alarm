package kr.ryan.alarm.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.alarm.adapter.viewholder.DateViewHolder
import kr.ryan.alarm.adapter.viewholder.MultiDayViewHolder
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.AlarmStatus
import kr.ryan.alarm.utility.AlarmDiffUtil

/**
 * Alarm
 * Class: AlarmAdapter
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
const val MULTIPLE = 0
const val SINGLE = 1

class AlarmAdapter : ListAdapter<Alarm, RecyclerView.ViewHolder>(AlarmDiffUtil()) {

    private var onItemClickEvent: ((Alarm) -> Unit)? = null
    fun setOnItemClickEvent(listener: (Alarm) -> Unit){
        onItemClickEvent = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            MULTIPLE ->{
                MultiDayViewHolder.from(parent, onItemClickEvent)
            }
            SINGLE ->{
                DateViewHolder.from(parent, onItemClickEvent)
            }
            else -> throw IllegalStateException("unKnwon Type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            MULTIPLE -> {
                if (holder is MultiDayViewHolder)
                    holder.bind(getItem(position))
            }
            SINGLE -> {
                if (holder is DateViewHolder)
                    holder.bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).alarmStatus){
            AlarmStatus.MULTIPLE_DAY -> MULTIPLE
            AlarmStatus.DATE -> SINGLE
        }
    }
}