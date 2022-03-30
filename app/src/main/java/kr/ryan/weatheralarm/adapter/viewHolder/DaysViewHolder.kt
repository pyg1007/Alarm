package kr.ryan.weatheralarm.adapter.viewHolder

import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.RecyclerDaysBinding
import kr.ryan.weatheralarm.util.convertTime
import kr.ryan.weatheralarm.util.getMeridiem
import kr.ryan.weatheralarm.util.onLongSingleClicks
import kr.ryan.weatheralarm.util.onSingleClicks
import timber.log.Timber
import java.util.*

/**
 * WeatherAlarm
 * Class: DaysViewHolder
 * Created by pyg10.
 * Created On 2021-10-26.
 * Description:
 */
@ExperimentalCoroutinesApi
class DaysViewHolder constructor(private val binding: RecyclerDaysBinding) : RecyclerView.ViewHolder(binding.root){

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

        binding.switchAlarmState.setOnCheckedChangeListener { componentButton, b ->
            alarmWithDate.alarm.onOff = binding.switchAlarmState.isChecked
            onSwitchClick(componentButton, alarmWithDate)
        }

    }

    private fun initBinding(alarmWithDate: AlarmWithDate){
        binding.run {
            title = alarmWithDate.alarm.title ?: ""
            alarmWithDate.alarmDate[0].date.also {
                meridiem = it.getMeridiem()
                time = it.convertTime()
            }

            val convertDateToBoolean = MutableList(7){false}
            alarmWithDate.alarmDate.forEach { alarmDate ->
                val index = Calendar.getInstance().apply {
                    time = alarmDate.date
                }.get(Calendar.DAY_OF_WEEK) - 1
                convertDateToBoolean[index] = !convertDateToBoolean[index]
            }

            days = convertDateToBoolean
            onOff = alarmWithDate.alarm.onOff
        }
    }

    companion object{

        private lateinit var onItemClick: (View, Int, AlarmWithDate) -> Unit
        private lateinit var onLongItemClick: (View, Int, AlarmWithDate) -> Unit
        private lateinit var onSwitchClick: (View, AlarmWithDate) -> Unit

        fun setOnItemClick(itemClick: (View, Int, AlarmWithDate) -> Unit){
            onItemClick = itemClick
        }

        fun setOnLongItemClick(itemClick: (View, Int, AlarmWithDate) -> Unit){
            onLongItemClick = itemClick
        }

        fun setOnSwitchClick(itemClick: (View, AlarmWithDate) -> Unit){
            onSwitchClick = itemClick
        }

    }

}