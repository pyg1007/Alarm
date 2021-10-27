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

    }

    companion object{

        private lateinit var onItemClick: (Alarm) -> Unit

        fun setOnItemClick(itemClick: (Alarm) -> Unit){
            onItemClick = itemClick
        }

    }
}