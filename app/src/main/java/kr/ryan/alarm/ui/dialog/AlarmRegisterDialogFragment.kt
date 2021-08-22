package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.application.AlarmApplication
import kr.ryan.alarm.databinding.FragmentAlarmDialogBinding
import kr.ryan.alarm.utility.createDraw
import kr.ryan.alarm.utility.dialogFragmentResize
import kr.ryan.alarm.utility.showShortToast
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

    init {

        lifecycleScope.launch {

            whenCreated {

                initDialog()

            }

            whenResumed {

                requireActivity().dialogFragmentResize(this@AlarmRegisterDialogFragment, 0.9f, 0.7f)

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initTimePicker()
        observeSelectDay()
    }

    private fun observeSelectDay() {
        alarmRegisterDialogViewModel.selectedDays.observe(viewLifecycleOwner) {
            clearView(binding.includeDays.rootViewGroup)
            if (!it.isNullOrEmpty()) {
                it.forEach { day -> createCircle(day) }
            }
        }
    }

    private fun observeCalendarClicked() {
        alarmRegisterDialogViewModel.clickCalendarIcon.observe(viewLifecycleOwner) {
            if (it) {

                val dialog = CalendarDialogFragment()
                if (!dialog.isAdded)
                    dialog.show(childFragmentManager, "Calendar")

                alarmRegisterDialogViewModel.clearCalendarIconClick()
            }
        }
    }

    private fun initBinding() {

        binding.apply {
            fragment = this@AlarmRegisterDialogFragment
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

    private fun initTime() {
        val hour = binding.tpSelect.hour
        val min = binding.tpSelect.minute

        alarmRegisterDialogViewModel.changeDate(hour, min)
    }

    private fun initTimePicker() {
        initTime()

        binding.tpSelect.setOnTimeChangedListener { _, hourOfDay, minute ->

            Log.e(TAG, "hour -> $hourOfDay, min -> $minute")
            alarmRegisterDialogViewModel.changeDate(hourOfDay, minute)
        }
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

    fun cancel() {
        requireContext().showShortToast("알람 추가 취소")
        dismissDialogFragment()
    }

    fun add() {
        requireContext().showShortToast("알람 추가 성공")
        dismissDialogFragment()
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