package kr.ryan.weatheralarm.future.activity

import androidx.activity.viewModels
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseActivity
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.adapter.AlarmAdapter
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.ActivityMainBinding
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import javax.inject.Inject

typealias AlarmListener = (Int, Alarm) -> Unit

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val alarmViewModel by viewModels<AlarmViewModel>()

    @Inject
    lateinit var alarmAdapter: AlarmAdapter


    init {

        lifecycleScope.launch {

            whenStarted {

            }

            whenCreated {
                initRecyclerView()
                recyclerViewItemClick()
                recyclerViewItemLongClick()
            }

            whenResumed {

            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                observeAlarmData()
            }

        }


    }

    private fun initRecyclerView() {
        binding.recyclerAlarm.apply {
            adapter = alarmAdapter
        }
    }

    private fun recyclerViewItemLongClick() {
        alarmAdapter.setOnLongClickListener { position, alarm ->
            Timber.d("$position recyclerView Long Click")
        }
    }

    private fun recyclerViewItemClick() {
        alarmAdapter.setOnClickListener { position, alarm ->
            Timber.d("$position recyclerView Click")
        }
    }

    private suspend fun observeAlarmData() {
        alarmViewModel.alarmList.collect {

            Timber.d("${it.size}")

            alarmAdapter.submitList(it.toMutableList())
        }
    }

}