package kr.ryan.alarm.ui.activity

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.adapter.AlarmAdapter
import kr.ryan.alarm.application.AlarmApplication
import kr.ryan.alarm.databinding.ActivityMainBinding
import kr.ryan.alarm.ui.dialog.AlarmRegisterDialogFragment
import kr.ryan.alarm.viewmodel.AlarmViewModel
import kr.ryan.alarm.viewmodel.factory.AlarmViewModelFactory
import kr.ryan.baseui.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val alarmViewModel by viewModels<AlarmViewModel> { AlarmViewModelFactory((application as AlarmApplication).repository) }

    private lateinit var alarmAdapter: AlarmAdapter

    init {

        lifecycleScope.launch {

            whenCreated {

                initBinding()

                initRecyclerView()

                recyclerViewItemClick()

                observeRecyclerViewData()

            }

        }


    }

    private fun initBinding() {

        binding.apply {
            activity = this@MainActivity
            viewModel = alarmViewModel
            lifecycleOwner = this@MainActivity
        }

    }

    fun showAlarmAddDialogFragment() {

        val dialog = AlarmRegisterDialogFragment()
        if (!dialog.isAdded)
            dialog.show(supportFragmentManager, "AlarmAdd")

    }

    private fun initRecyclerView() {
        alarmAdapter = AlarmAdapter().apply {
            binding.recyclerAlarm.adapter = this
        }
    }

    private fun recyclerViewItemClick() {
        if (!::alarmAdapter.isInitialized) return

        alarmAdapter.setOnItemClickEvent {
            Log.e(TAG, "onClicked")
            //TODO To Edit Mode Alarm Screen
        }
    }

    private fun observeRecyclerViewData() {

        alarmViewModel.alarmList.observe(this@MainActivity) {
            Log.e(TAG, "RecyclerView Data -> $it")
            if (::alarmAdapter.isInitialized)
                alarmAdapter.submitList(it.toMutableList())
        }

    }

    companion object {
        const val TAG = "MainActivity"
    }

}