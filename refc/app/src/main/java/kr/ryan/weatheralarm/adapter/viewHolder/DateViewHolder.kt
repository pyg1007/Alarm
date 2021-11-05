package kr.ryan.weatheralarm.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.RecyclerDateBinding

/**
 * WeatherAlarm
 * Class: DateViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class DateViewHolder constructor(private val binding: RecyclerDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(alarm: Alarm){
        initBinding(alarm)

        binding.root.setOnLongClickListener {
            onLongItemClick(adapterPosition, alarm)
            true
        }

        binding.root.setOnClickListener {
            onItemClick(adapterPosition, alarm)
        }
    }

    private fun initBinding(alarm: Alarm){
        binding.alarm = alarm
    }

    companion object{

        private lateinit var onItemClick: (Int, Alarm) -> Unit
        private lateinit var onLongItemClick: (Int, Alarm) -> Unit

        fun setOnItemClick(itemClick: (Int, Alarm) -> Unit){
            onItemClick = itemClick
        }

        fun setOnLongItemClick(itemClick: (Int, Alarm) -> Unit){
            onLongItemClick = itemClick
        }

    }
}