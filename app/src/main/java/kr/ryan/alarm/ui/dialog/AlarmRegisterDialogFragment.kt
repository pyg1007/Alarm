package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
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

    private val alarmRegisterDialogViewModel by viewModels<AlarmRegisterViewModel> { AlarmRegisterViewModelFactory((requireActivity().application as AlarmApplication).repository) }

    private val multiDaysSelected = Array(7) { false }

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

    private fun check(){
        alarmRegisterDialogViewModel.selectedDays.observe(viewLifecycleOwner){
            Log.e(TAG, it.joinToString(","))
        }
    }

    private fun createCircle(view: View) {

        if (view is ConstraintLayout) {
            val circle: Drawable?
            when (view) {
                binding.includeDays.constSunday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "일")
                    binding.includeDays.tvSunday.background = circle
                    multiDaysSelected[0] = !multiDaysSelected[0]
                }
                binding.includeDays.constMonday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "월")
                    binding.includeDays.tvMonday.background = circle
                    multiDaysSelected[1] = !multiDaysSelected[1]
                }
                binding.includeDays.constTuesday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "화")
                    binding.includeDays.tvTuesday.background = circle
                    multiDaysSelected[2] = !multiDaysSelected[2]
                }
                binding.includeDays.constWednesday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "수")
                    binding.includeDays.tvWednesday.background = circle
                    multiDaysSelected[3] = !multiDaysSelected[3]
                }
                binding.includeDays.constThursday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "목")
                    binding.includeDays.tvThursday.background = circle
                    multiDaysSelected[4] = !multiDaysSelected[4]
                }
                binding.includeDays.constFriday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "금")
                    binding.includeDays.tvFriday.background = circle
                    multiDaysSelected[5] = !multiDaysSelected[5]
                }
                binding.includeDays.constSaturday -> {
                    circle = requireContext().createDraw(R.drawable.day_clicked_circle, "토")
                    binding.includeDays.tvSaturday.background = circle
                    multiDaysSelected[6] = !multiDaysSelected[6]
                }
            }

        }

    }

    private fun removeCircle(view: View) {

        if (view is ConstraintLayout) {
            when (view) {
                binding.includeDays.constSunday -> {
                    binding.includeDays.tvSunday.background = null
                    multiDaysSelected[0] = !multiDaysSelected[0]
                }
                binding.includeDays.constMonday -> {
                    binding.includeDays.tvMonday.background = null
                    multiDaysSelected[1] = !multiDaysSelected[1]
                }
                binding.includeDays.constTuesday -> {
                    binding.includeDays.tvTuesday.background = null
                    multiDaysSelected[2] = !multiDaysSelected[2]
                }
                binding.includeDays.constWednesday -> {
                    binding.includeDays.tvWednesday.background = null
                    multiDaysSelected[3] = !multiDaysSelected[3]
                }
                binding.includeDays.constThursday -> {
                    binding.includeDays.tvThursday.background = null
                    multiDaysSelected[4] = !multiDaysSelected[4]
                }
                binding.includeDays.constFriday -> {
                    binding.includeDays.tvFriday.background = null
                    multiDaysSelected[5] = !multiDaysSelected[5]
                }
                binding.includeDays.constSaturday -> {
                    binding.includeDays.tvSaturday.background = null
                    multiDaysSelected[6] = !multiDaysSelected[6]
                }
            }

        }

    }

    private fun initBinding() {

        binding.apply {
            fragment = this@AlarmRegisterDialogFragment
            lifecycleOwner = viewLifecycleOwner
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