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
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.application.AlarmApplication
import kr.ryan.alarm.data.Days
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

                setInitDialog()

            }

            whenResumed {

                requireActivity().dialogFragmentResize(this@AlarmRegisterDialogFragment, 0.9f, 0.7f)

            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        check()
    }

    private fun check() {
        alarmRegisterDialogViewModel.selectedDays.observe(viewLifecycleOwner) {
            clearView(binding.includeDays.rootViewGroup)
            if (!it.isNullOrEmpty()) {
                it.forEach { days -> createCircle(days) }
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

    private fun createCircle(days: Days) {
        when (days.day) {
            "일" -> {
                binding.includeDays.tvSunday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "월" -> {
                binding.includeDays.tvMonday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "화" -> {
                binding.includeDays.tvTuesday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "수" -> {
                binding.includeDays.tvWednesday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "목" -> {
                binding.includeDays.tvThursday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "금" -> {
                binding.includeDays.tvFriday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
            }
            "토" -> {
                binding.includeDays.tvSaturday.background =
                    requireContext().createDraw(R.drawable.day_clicked_circle, days.day)
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

    private fun setInitDialog() {
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