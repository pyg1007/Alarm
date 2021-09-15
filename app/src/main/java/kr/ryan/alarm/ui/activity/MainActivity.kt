package kr.ryan.alarm.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.PopupMenu
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

                recyclerViewItemLongClick()

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

        alarmAdapter.setOnItemClickEvent {alarm, _->
            Log.e(TAG, "onClicked")
            val dialog = AlarmRegisterDialogFragment()
            val bundle = Bundle().apply {
                putParcelable("Alarm", alarm)
            }
            dialog.arguments = bundle
            if (!dialog.isAdded)
                dialog.show(supportFragmentManager, "AlarmAdd")
        }
    }

    private fun recyclerViewItemLongClick() {
        if (!::alarmAdapter.isInitialized) return

        alarmAdapter.setOnItemLongClickEvent { alarm, view ->
            val menu = PopupMenu(this@MainActivity, view, Gravity.END)
            menuInflater.inflate(R.menu.popup_menu, menu.menu)
            menu.setOnMenuItemClickListener {
                if (it.itemId == R.id.popup_del){
                    alarmViewModel.deleteAlarm(alarm)
                }

                false
            }
            menu.show()
        }
    }

    private fun observeRecyclerViewData() {

        alarmViewModel.alarmList.observe(this@MainActivity) {
            if (::alarmAdapter.isInitialized)
                alarmAdapter.submitList(it.toMutableList())
        }

    }

    companion object {
        const val TAG = "MainActivity"
    }

}