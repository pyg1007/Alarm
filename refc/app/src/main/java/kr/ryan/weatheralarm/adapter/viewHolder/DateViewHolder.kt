package kr.ryan.weatheralarm.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmStatus
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.RecyclerDateBinding

/**
 * WeatherAlarm
 * Class: DateViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
class DateViewHolder constructor(private val binding: RecyclerDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(status: AlarmWithDate){
        //initBinding(status)

        binding.root.setOnLongClickListener {
            onLongItemClick(it, adapterPosition, status)
            true
        }

        binding.root.setOnClickListener {
            onItemClick(it, adapterPosition, status)
        }
    }

//    private fun initBinding(status: AlarmStatus){
//        binding.alarm = status as AlarmStatus.DateAlarm
//    }

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