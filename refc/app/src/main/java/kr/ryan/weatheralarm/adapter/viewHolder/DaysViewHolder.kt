package kr.ryan.weatheralarm.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.RecyclerDaysBinding

/**
 * WeatherAlarm
 * Class: DaysViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class DaysViewHolder constructor(private val binding: RecyclerDaysBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(alarm: Alarm){



        binding.root.setOnLongClickListener {
            onLongItemClick(adapterPosition, alarm)
            true
        }

        binding.root.setOnClickListener {
            onItemClick(adapterPosition, alarm)
        }
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