package kr.ryan.weatheralarm.future.activity

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
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
import kr.ryan.weatheralarm.future.dialog.AlarmDialogFragment
import kr.ryan.weatheralarm.util.convertAlarm
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val alarmViewModel by viewModels<AlarmViewModel>()

    @Inject
    lateinit var alarmAdapter: AlarmAdapter

    @Inject
    lateinit var dialogFragment: AlarmDialogFragment

    init {

        lifecycleScope.launch {

            whenStarted {

            }

            whenCreated {
                initBinding()
                initRecyclerView()
                recyclerViewItemClick()
                recyclerViewItemLongClick()
            }

            whenResumed {

            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeAlarmData()
                }

                launch {
                    observeAddBtn()
                }

                launch {
                    observeMoreBtn()
                }

            }

        }


    }

    private fun initBinding() {
        binding.apply {
            viewModel = alarmViewModel
            lifecycleOwner = this@MainActivity
        }
    }

    private fun initRecyclerView() {
        binding.recyclerAlarm.apply {
            adapter = alarmAdapter
        }
    }

    private fun recyclerViewItemLongClick() {
        alarmAdapter.setOnLongClickListener { view, position, alarm ->
            val popup = PopupMenu(this@MainActivity, view, Gravity.END)
            menuInflater.inflate(R.menu.item_popup_alarm, popup.menu)
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_delete){
                    alarmViewModel.deleteAlarm(alarm.convertAlarm())
                }
                false
            }
            popup.show()
        }
    }

    private fun recyclerViewItemClick() {
        alarmAdapter.setOnClickListener { _, position, alarm ->
            openAlarmDialog(alarm.convertAlarm())
            Timber.d("$position recyclerView Click")
        }
    }

    private fun openAlarmDialog(alarm: Alarm?){
        alarm?.let { data ->
            val bundle = Bundle().also {
                it.putParcelable("alarm", data)
            }
            dialogFragment.arguments = bundle
        }
        dialogFragment.show(supportFragmentManager, "Alarm")
    }

    private suspend fun observeAddBtn() {

        alarmViewModel.onClickAdd.collect {
            if (it) {
                openAlarmDialog(null)
                alarmViewModel.initAddState()
            }
        }

    }

    private suspend fun observeMoreBtn() {

        alarmViewModel.onClickMore.collect {
            if (it) {
                Timber.d("OnClick More")
                alarmViewModel.initMoreState()
            }
        }

    }

    private suspend fun observeAlarmData() {
        alarmViewModel.alarmList.collect {

            Timber.d("${it.size}")

            alarmAdapter.submitList(it.toMutableList())
        }
    }

}