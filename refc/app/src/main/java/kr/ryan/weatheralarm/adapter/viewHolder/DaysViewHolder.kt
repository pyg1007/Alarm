package kr.ryan.weatheralarm.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.RecyclerDaysBinding
import kr.ryan.weatheralarm.util.convertTime
import kr.ryan.weatheralarm.util.getMeridiem
import java.util.*

/**
 * WeatherAlarm
 * Class: DaysViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class DaysViewHolder constructor(private val binding: RecyclerDaysBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(alarmWithDate: AlarmWithDate){

        initBinding(alarmWithDate)

        binding.root.setOnLongClickListener {
            onLongItemClick(it, adapterPosition, alarmWithDate)
            true
        }

        binding.root.setOnClickListener {
            onItemClick(it, adapterPosition, alarmWithDate)
        }
    }

    private fun initBinding(alarmWithDate: AlarmWithDate){
        binding.run {
            title = alarmWithDate.alarm.title ?: ""
            alarmWithDate.alarmDate[0].date.also {
                meridiem = it.getMeridiem()
                time = it.convertTime()
            }
            days = alarmWithDate.alarmDate.map { Calendar.getInstance().apply {
                time = it.date
            }.get(Calendar.DAY_OF_WEEK) }
            onOff = alarmWithDate.alarm.onOff
        }
    }

    companion object{

        private lateinit var onItemClick: (View, Int, AlarmWithDate) -> Unit
        private lateinit var onLongItemClick: (View, Int, AlarmWithDate) -> Unit

        fun setOnItemClick(itemClick: (View, Int, AlarmWithDate) -> Unit){
            onItemClick = itemClick
        }

        fun setOnLongItemClick(itemClick: (View, Int, AlarmWithDate) -> Unit){
            onLongItemClick = itemClick
        }

    }

}