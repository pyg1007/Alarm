package kr.ryan.weatheralarm.future.activity

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.Gravity
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseActivity
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.adapter.AlarmAdapter
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.ActivityMainBinding
import kr.ryan.weatheralarm.future.dialog.AlarmDialogFragment
import kr.ryan.weatheralarm.util.AlarmEvent
import kr.ryan.weatheralarm.util.Route
import kr.ryan.weatheralarm.util.UiEvent
import kr.ryan.weatheralarm.util.registerAlarm
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val alarmViewModel by viewModels<AlarmViewModel>()

    @Inject
    lateinit var alarmAdapter: AlarmAdapter

    private var dialogFragment: AlarmDialogFragment? = null

    private var alarmList: List<AlarmWithDate>? = null

    init {

        lifecycleScope.launch {

            whenCreated {
                initBinding()
                initRecyclerView()
                recyclerViewItemClick()
                recyclerViewItemLongClick()
                testAlarm()
            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeUiState()
                }

                launch {
                    observeAlarmWithDate()
                }
            }
        }
    }

    private fun testAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= 31){
            when{
                alarmManager.canScheduleExactAlarms() -> {
                    Timber.d("Grant Permission")
                }
                else -> {
                    Intent().apply {
                        action = ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    }.also {
                        startActivity(it)
                    }
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
        alarmAdapter.setOnLongClickListener { view, position, _ ->
            val popup = PopupMenu(this@MainActivity, view, Gravity.END)
            menuInflater.inflate(R.menu.item_popup_alarm, popup.menu)
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_delete) {
                    alarmList?.let { alarmDate ->
                        alarmViewModel.onEvent(AlarmEvent.OnDeleteClick(alarmDate[position]))
                    }
                }
                false
            }
            popup.show()
        }
    }

    private fun recyclerViewItemClick() {
        alarmAdapter.setOnClickListener { _, position, _ ->
            alarmList?.let {
                alarmViewModel.onEvent(AlarmEvent.OnAlarmClick(it[position]))
            }
        }
    }

    private fun openAlarmDialog(alarmWithDate: AlarmWithDate?) {
        dialogFragment = AlarmDialogFragment()
        alarmWithDate?.let { data ->
            val bundle = Bundle().also {
                it.putParcelable("alarmWithDate", data)
            }
            dialogFragment?.arguments = bundle
        }

        dialogFragment?.show(supportFragmentManager, "Alarm")
        responseAlarmDialog()
    }

    private fun responseAlarmDialog() {
        AlarmDialogFragment.setOnCancelEvent {
            Timber.d("Cancel Active")
            alarmViewModel.sendEvent(UiEvent.ShowSnackBar("취소하였습니다."))
        }

        AlarmDialogFragment.setOnSaveEvent {
            Timber.d("Save Active")
            alarmViewModel.sendEvent(UiEvent.ShowSnackBar("저장되었습니다."))
        }
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    if (it.route == Route.ADD_MODE)
                        openAlarmDialog(null)
                    else
                        openAlarmDialog(it.alarmWithDate)
                }
                is UiEvent.PopUpStack -> {

                }
                is UiEvent.ShowSnackBar -> {
                    val snackBar =
                        Snackbar.make(binding.constRoot, it.message, Snackbar.LENGTH_SHORT)
                    snackBar.setAction(it.action) {
                        alarmViewModel.deleteAlarmDate?.let { deletedAlarm ->
                            alarmViewModel.onEvent(AlarmEvent.OnUndoDeleteClick(deletedAlarm))
                        }
                        snackBar.dismiss()
                    }
                    snackBar.show()
                }
            }
        }
    }

    private fun observeAlarmWithDate() {
        alarmViewModel.alarmList.observe(this, Observer {
            Timber.d("Relation -> $it")
            alarmList = it
            alarmAdapter.submitList(it.toMutableList())
        })
    }

}