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
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.util.AlarmWithDateDiffUtil

/**
 * WeatherAlarm
 * Class: AlarmAdapter
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
const val DAYS = 1
const val DATE = 2

class AlarmAdapter : ListAdapter<AlarmWithDate, RecyclerView.ViewHolder>(AlarmWithDateDiffUtil()) {

    private lateinit var _onClickEvent: (View, Int, AlarmWithDate) -> Unit
    private lateinit var _onLongClickEvent: (View, Int, AlarmWithDate) -> Unit

    fun setOnClickListener(clickListener: (View, Int, AlarmWithDate) -> Unit) {
        _onClickEvent = clickListener
    }

    fun setOnLongClickListener(longClickListener: (View, Int, AlarmWithDate) -> Unit) {
        _onLongClickEvent = longClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DAYS -> {
                DaysViewHolder.setOnItemClick(_onClickEvent)
                DaysViewHolder.setOnLongItemClick(_onLongClickEvent)
                DaysViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.recycler_days,
                        parent,
                        false
                    )
                )
            }
            DATE -> {
                DateViewHolder.setOnItemClick(_onClickEvent)
                DateViewHolder.setOnLongItemClick(_onLongClickEvent)
                DateViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.recycler_date,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalStateException("unKnown ViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATE -> (holder as DateViewHolder).bind(getItem(position))
            DAYS -> (holder as DaysViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).alarmDate.size) {
            1 -> DATE
            else -> DAYS
        }
    }
}