package kr.ryan.weatheralarm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.adapter.viewHolder.DateViewHolder
import kr.ryan.weatheralarm.adapter.viewHolder.DaysViewHolder
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmStatus
import kr.ryan.weatheralarm.util.AlarmDiffUtil

/**
 * WeatherAlarm
 * Class: AlarmAdapter
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
const val DAYS = 1
const val DATE = 2

class AlarmAdapter: ListAdapter<Alarm, RecyclerView.ViewHolder>(AlarmDiffUtil()) {

    private lateinit var _onClickEvent: (View, Int, AlarmStatus) -> Unit
    private lateinit var _onLongClickEvent: (View, Int, AlarmStatus) -> Unit

    fun setOnClickListener(clickListener: (View, Int, AlarmStatus) -> Unit){
        _onClickEvent = clickListener
    }

    fun setOnLongClickListener(longClickListener: (View, Int, AlarmStatus) -> Unit){
        _onLongClickEvent = longClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            DAYS -> {
                DaysViewHolder.setOnItemClick(_onClickEvent)
                DaysViewHolder.setOnLongItemClick(_onLongClickEvent)
                DaysViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_days, parent, false))
            }
            DATE -> {
                DateViewHolder.setOnItemClick(_onClickEvent)
                DateViewHolder.setOnLongItemClick(_onLongClickEvent)
                DateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_date, parent, false))
            }
            else -> throw IllegalStateException("unKnown ViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when(getItemViewType(position)){
//            DATE -> (holder as DateViewHolder).bind(convertAlarmStatus(getItem(position)))
//            DAYS -> (holder as DaysViewHolder).bind(convertAlarmStatus(getItem(position)))
//        }
    }
}