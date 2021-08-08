package kr.ryan.alarm.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ryan.alarm.R
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.databinding.RecyclerDateBinding

/**
 * Alarm
 * Class: DateViewHolder
 * Created by pyg10.
 * Created On 2021-08-08.
 * Description:
 */
class DateViewHolder private constructor(private val binding: RecyclerDateBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(alarm: Alarm){

    }

    companion object{

        private var itemClickEvent: ((Alarm) -> Unit)? = null

        fun from(parent: ViewGroup, onItemClickEventListener: ((Alarm) -> Unit)?) : DateViewHolder{
            itemClickEvent = onItemClickEventListener
            return DateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_date, parent, false))
        }
    }

}