package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ryan.alarm.R
import kr.ryan.alarm.application.AlarmApplication
import kr.ryan.alarm.data.Alarm
import kr.ryan.alarm.data.UiStatus
import kr.ryan.alarm.databinding.FragmentAlarmDialogBinding
import kr.ryan.alarm.utility.createDraw
import kr.ryan.alarm.utility.dialogFragmentResize
import kr.ryan.alarm.viewmodel.AlarmRegisterViewModel
import kr.ryan.alarm.viewmodel.factory.AlarmRegisterViewModelFactory
import kr.ryan.baseui.BaseDialogFragment

/**
 * Alarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-08-07.
 * Description:
 */
class AlarmRegisterDialogFragment :
    BaseDialogFragment<FragmentAlarmDialogBinding>(R.layout.fragment_alarm_dialog) {

    private val alarmRegisterDialogViewModel by viewModels<AlarmRegisterViewModel> {
        AlarmRegisterViewModelFactory(
            (requireActivity().application as AlarmApplication).repository
        )
    }

    private var editAlarm: Alarm? = null

    init {

        lifecycleScope.launch {

            whenCreated {

                initDialog()
                getEditModeDate()

            }

            whenResumed {

                requireActivity().dialogFragmentResize(this@AlarmRegisterDialogFragment, 0.9f, 0.7f)

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initEditMode()
        observeSelectDay()
        observeEditText()
        observeUiStatus()
        observeCalenderEvent()
    }

    private fun getEditModeDate() {
        arguments?.let {
            editAlarm = it.getParcelable("Alarm")
        }
    }

    private fun initEditMode() {

        editAlarm?.let {
            alarmRegisterDialogViewModel.changeTitle(it.title)
            if (!it.isSingleAlarm)
                alarmRegisterDialogViewModel.changeSelectedDay(it.alarmTimeList)
            alarmRegisterDialogViewModel.changeSelectedDate(it.alarmTimeList[0])
        }

    }

    private fun observeSelectDay() = CoroutineScope(Dispatchers.Default).launch {
        alarmRegisterDialogViewModel.createCircleDay.collect {
            withContext(Dispatchers.Main) {
                clearView(binding.includeDays.rootViewGroup)
                if (!it.isNullOrEmpty()) {
                    it.forEach { day -> createCircle(day) }
                }
            }
        }
    }

    private fun observeCalenderEvent() = CoroutineScope(Dispatchers.Default).launch {

        alarmRegisterDialogViewModel.onClickCalendar.collect {
            if (it) {

                val dialogFragment = CalendarDialogFragment()
                CalendarDialogFragment.setOnAddClickListener { date ->
                    alarmRegisterDialogViewModel.changeSelectedDate(date)
                }
                dialogFragment.show(parentFragmentManager, "Calendar")

                alarmRegisterDialogViewModel.initCalendarEvent()
            }
        }

    }

    private fun observeUiStatus() = CoroutineScope(Dispatchers.Default).launch {
        alarmRegisterDialogViewModel.uiStatus.collect { status ->
            when (status) {
                is UiStatus.Init -> {
                    Log.e(TAG, "init")
                }
                is UiStatus.Loading -> {
                    Log.e(TAG, "Loading")
                }
                is UiStatus.Complete -> {
                    Log.e(TAG, "Complete")
                    dismissDialogFragment()
                }
            }
        }
    }

    private fun initBinding() {

        binding.apply {
            alarm = editAlarm
            lifecycleOwner = viewLifecycleOwner
            viewModel = alarmRegisterDialogViewModel
            includeDays.viewModel = alarmRegisterDialogViewModel
        }

    }

    private fun initDialog() {
        val layoutParams = WindowManager.LayoutParams().apply {
            flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.8f
        }

        dialog?.window?.apply {
            attributes = layoutParams
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        isCancelable = false
    }

    private fun observeEditText() {
        binding.etAlarmName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    alarmRegisterDialogViewModel.changeTitle(it.toString())
                }
            }

        })
    }

    private fun createCircle(day: Int) {
        when (day) {
            1 -> {
                binding.includeDays.tvSunday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            2 -> {
                binding.includeDays.tvMonday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            3 -> {
                binding.includeDays.tvTuesday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            4 -> {
                binding.includeDays.tvWednesday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            5 -> {
                binding.includeDays.tvThursday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            6 -> {
                binding.includeDays.tvFriday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
            7 -> {
                binding.includeDays.tvSaturday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, day)
            }
        }
    }

    private fun clearView(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            when (val view = viewGroup.getChildAt(i)) {
                is TextView -> {
                    view.background = null
                }
                is ConstraintLayout -> {
                    clearView(view)
                }
            }
        }
    }

    private fun dismissDialogFragment() {
        parentFragmentManager.findFragmentByTag("AlarmAdd")?.let {
            (it as DialogFragment).dismiss()
        }
    }

    companion object {
        const val TAG = "AlarmDialogFragment"
    }
}