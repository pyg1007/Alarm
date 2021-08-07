package kr.ryan.alarm.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.utility.AlarmDiffUtil

/**
 * Alarm
 * Class: AlarmAdapter
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
class AlarmAdapter : ListAdapter<Alarm, RecyclerView.ViewHolder>(AlarmDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}