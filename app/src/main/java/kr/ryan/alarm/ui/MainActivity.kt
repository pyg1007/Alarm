package kr.ryan.alarm.ui

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.adapter.AlarmAdapter
import kr.ryan.alarm.application.AlarmApplication
import kr.ryan.alarm.databinding.ActivityMainBinding
import kr.ryan.alarm.viewmodel.AlarmViewModel
import kr.ryan.alarm.viewmodel.factory.AlarmViewModelFactory
import kr.weather.baseui.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val alarmViewModel by viewModels<AlarmViewModel> { AlarmViewModelFactory((application as AlarmApplication).repository) }

    private lateinit var alarmAdapter: AlarmAdapter

    init {

        lifecycleScope.launch {

            whenCreated {

                initRecyclerView()

                observeRecyclerViewData()

            }

        }


    }

    private fun initRecyclerView(){
        alarmAdapter = AlarmAdapter().apply {
            binding.recyclerAlarm.adapter = this
        }
    }

    private fun observeRecyclerViewData(){

        alarmViewModel.alarmList.observe(this@MainActivity){
            if (::alarmAdapter.isInitialized)
                alarmAdapter.submitList(it.toMutableList())
        }

    }

}