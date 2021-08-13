package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.FragmentAlarmDialogBinding
import kr.ryan.alarm.utility.dialogFragmentResize
import kr.ryan.alarm.utility.showShortToast
import kr.ryan.alarm.viewmodel.AlarmViewModel
import kr.ryan.baseui.BaseDialogFragment

/**
 * Alarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-08-07.
 * Description:
 */
class AlarmDialogFragment :
    BaseDialogFragment<FragmentAlarmDialogBinding>(R.layout.fragment_alarm_dialog) {

    private val alarmDialogFragment by activityViewModels<AlarmViewModel>()

    init {

        lifecycleScope.launch {

            whenCreated {

                Log.e(TAG, "onCreate")
                setInitDialog()

            }

            whenResumed {

                Log.e(TAG, "onResume")
                requireActivity().dialogFragmentResize(this@AlarmDialogFragment, 0.9f, 0.7f)

            }

        }
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated")
        initBinding()
    }



    private fun initBinding() {

        binding.apply {
            fragment = this@AlarmDialogFragment
            lifecycleOwner = viewLifecycleOwner
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

    private fun dismissDialogFragment(){
        parentFragmentManager.findFragmentByTag("AlarmAdd")?.let {
            (it as DialogFragment).dismiss()
        }
    }


    companion object {
        const val TAG = "AlarmDialogFragment"
    }
}