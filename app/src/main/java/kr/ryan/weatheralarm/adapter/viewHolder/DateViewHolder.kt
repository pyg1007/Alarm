package kr.ryan.weatheralarm.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.RecyclerDateBinding
import kr.ryan.weatheralarm.util.*

/**
 * WeatherAlarm
 * Class: DateViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
@ExperimentalCoroutinesApi
class DateViewHolder constructor(private val binding: RecyclerDateBinding) : RecyclerView.ViewHolder(binding.root) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun bind(alarmWithDate: AlarmWithDate){
        initBinding(alarmWithDate)

        binding.root.onSingleClicks().onEach {
            onItemClick(binding.root, adapterPosition, alarmWithDate)
        }.launchIn(
            coroutineScope
        )

        binding.root.onLongSingleClicks().onEach {
            onLongItemClick(binding.root, adapterPosition, alarmWithDate)
        }.launchIn(
            coroutineScope
        )
    }

    private fun initBinding(alarmWithDate: AlarmWithDate){
        binding.run {
            title = alarmWithDate.alarm.title ?: ""
            alarmWithDate.alarmDate[0].date.also {
                date = it.convertDateWithDayToString()
                meridiem = it.getMeridiem()
                time = it.convertTime()
            }
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