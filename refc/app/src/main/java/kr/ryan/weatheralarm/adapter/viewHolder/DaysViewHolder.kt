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

    fun bind(){

    }

    companion object{

        private lateinit var onItemClick: (Alarm) -> Unit

        fun setOnItemClick(itemClick: (Alarm) -> Unit){
            onItemClick = itemClick
        }

    }

}