package kr.ryan.weatheralarm.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmStatus
import kr.ryan.weatheralarm.databinding.RecyclerDaysBinding

/**
 * WeatherAlarm
 * Class: DaysViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class DaysViewHolder constructor(private val binding: RecyclerDaysBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(status: AlarmStatus){

        initBinding(status)

        binding.root.setOnLongClickListener {
            onLongItemClick(adapterPosition, status)
            true
        }

        binding.root.setOnClickListener {
            onItemClick(adapterPosition, status)
        }
    }

    private fun initBinding(status: AlarmStatus){
        binding.alarm = status as AlarmStatus.DaysAlarm
    }

    companion object{

        private lateinit var onItemClick: (Int, AlarmStatus) -> Unit
        private lateinit var onLongItemClick: (Int, AlarmStatus) -> Unit

        fun setOnItemClick(itemClick: (Int, AlarmStatus) -> Unit){
            onItemClick = itemClick
        }

        fun setOnLongItemClick(itemClick: (Int, AlarmStatus) -> Unit){
            onLongItemClick = itemClick
        }

    }

}