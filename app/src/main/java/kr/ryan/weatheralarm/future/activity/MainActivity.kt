package kr.ryan.weatheralarm.future.activity

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseActivity
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.adapter.AlarmAdapter
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.ActivityMainBinding
import kr.ryan.weatheralarm.future.dialog.AlarmDialogFragment
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import kr.ryan.weatheralarm.viewModel.RemainTimerViewModel
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val alarmViewModel by viewModels<AlarmViewModel>()

    private val loopRemainTimer by viewModels<RemainTimerViewModel>()

    private val alarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Inject
    lateinit var alarmAdapter: AlarmAdapter

    private var dialogFragment: AlarmDialogFragment? = null

    private var alarmList: List<AlarmWithDate>? = null

    init {

        lifecycleScope.launch {

            whenStarted {
                checkAlarmPermission()
            }

            whenCreated {
                initBinding()
                initRecyclerView()
                recyclerViewItemClick()
                recyclerViewItemLongClick()
                recyclerSwitchClick()
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

    private fun checkAlarmPermission() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= 31) {
            when {
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
            loopViewModel = loopRemainTimer
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
            showDeletePopupMenu(createPopupMenu(view, R.menu.item_popup_alarm), position)
        }
    }

    private fun recyclerViewItemClick() {
        alarmAdapter.setOnClickListener { _, position, _ ->
            alarmList?.let {
                alarmViewModel.onEvent(AlarmEvent.OnAlarmClick(it[position]))
            }
        }
    }

    private fun recyclerSwitchClick(){
        alarmAdapter.setOnSwitchClickListener { _, alarmWithDate ->
            alarmViewModel.onEvent(AlarmEvent.OnUpdate(alarmWithDate))
            Timber.d("$alarmWithDate")
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

        dialogFragment?.let {
            if (!it.isAdded) {
                dialogFragment?.show(supportFragmentManager, "Alarm")
                responseAlarmDialog()
            }
        }
    }

    private fun responseAlarmDialog() {
        AlarmDialogFragment.setOnCancelEvent {
            alarmViewModel.sendEvent(UiEvent.ShowSnackBar("취소하였습니다."))
        }

        AlarmDialogFragment.setOnSaveEvent {
            alarmViewModel.sendEvent(UiEvent.ShowSnackBar(it ?: "저장되었습니다."))
        }
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    when (it.route) {
                        Route.ADD_MODE -> {
                            openAlarmDialog(null)
                        }
                        Route.EDIT_MODE -> {
                            openAlarmDialog(it.alarmWithDate)
                        }
                        else -> {
                            showAllDeletePopupMenu(
                                createPopupMenu(
                                    binding.ivMore,
                                    R.menu.item_popup_all_delete
                                )
                            )
                        }
                    }
                }
                is UiEvent.PopUpStack -> {

                }
                is UiEvent.ShowSnackBar -> {
                    val snackBar =
                        Snackbar.make(binding.constRoot, it.message, Snackbar.LENGTH_SHORT)
                    snackBar.setAction(it.action) {
                        alarmViewModel.deleteAlarmDate?.let { deletedAlarm ->
                            alarmViewModel.onEvent(AlarmEvent.OnUndoDeleteClick(deletedAlarm))
                            if (deletedAlarm.alarm.onOff) {
                                if (isRegisterAlarm(deletedAlarm)) {
                                    alarmManager.cancelAlarm(this@MainActivity, deletedAlarm)
                                }
                                alarmManager.registerAlarm(this@MainActivity, deletedAlarm)
                            }
                        }
                        snackBar.dismiss()
                    }
                    snackBar.show()
                }
            }
        }
    }

    private fun createPopupMenu(view: View, @MenuRes menuId: Int): PopupMenu {
        val popupMenu = PopupMenu(this, view, Gravity.END)
        menuInflater.inflate(menuId, popupMenu.menu)
        return popupMenu
    }

    private fun showDeletePopupMenu(popupMenu: PopupMenu, position: Int) {
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_delete) {
                alarmList?.let { alarmDateList ->
                    if (isRegisterAlarm(alarmDateList[position])) {
                        alarmManager.cancelAlarm(this@MainActivity, alarmDateList[position])
                    }
                    alarmViewModel.onEvent(AlarmEvent.OnDeleteClick(alarmDateList[position]))
                }
            }
            false
        }
        popupMenu.show()
    }

    private fun showAllDeletePopupMenu(popupMenu: PopupMenu) {
        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_all_delete) {
                alarmList?.let { alarmDateList ->
                    alarmDateList.forEach { alarmWithDate ->
                        if (isRegisterAlarm(alarmWithDate)) {
                            alarmManager.cancelAlarm(this@MainActivity, alarmWithDate)
                        }
                    }
                    alarmViewModel.onEvent(AlarmEvent.OnAllDeleteClick(alarmDateList.map { list -> list.alarm }))
                }
            }
            false
        }
        popupMenu.show()
    }

    private suspend fun observeAlarmWithDate() {
        alarmViewModel.alarmList.collect {
            alarmList = it
            alarmAdapter.submitList(it.toMutableList())
        }
    }

}