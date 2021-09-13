package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CalendarView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.FragmentCalendarDialogBinding
import kr.ryan.alarm.utility.dateToString
import kr.ryan.alarm.utility.dialogFragmentResize
import kr.ryan.alarm.viewmodel.AlarmDateSelectViewModel
import kr.ryan.baseui.BaseDialogFragment
import java.util.*

/**
 * Alarm
 * Class: CalendarDialogFragment
 * Created by pyg10.
 * Created On 2021-08-23.
 * Description:
 */
class CalendarDialogFragment :
    BaseDialogFragment<FragmentCalendarDialogBinding>(R.layout.fragment_calendar_dialog) {

    private val calendarViewModel by viewModels<AlarmDateSelectViewModel>()

    init {
        lifecycleScope.launch {

            whenCreated {

                initDialog()

            }

            whenResumed {

                requireActivity().dialogFragmentResize(this@CalendarDialogFragment, 1.0f, 0.6f)
                dialog?.window?.setGravity(Gravity.BOTTOM)

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeMinDate()
        initBinding()
        observeAddStatus()
        observeCancelStatus()
    }

    private fun changeMinDate(){
        binding.calendar.minDate = Date().time
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

    private fun initBinding() {
        binding.viewModel = calendarViewModel
    }

    fun receiveDate(result: (Date) -> Unit){
        result(calendarViewModel.selectDate)
    }

    private fun observeAddStatus(){
        calendarViewModel.add.observe(viewLifecycleOwner){
            if (it){
                selectedDate(calendarViewModel.selectDate)
                calendarViewModel.clearAddBtnStatus()
                closeFragmentDialog()
            }
        }
    }

    private fun observeCancelStatus(){
        calendarViewModel.cancel.observe(viewLifecycleOwner){
            if (it){
                calendarViewModel.clearCancelBtnStatus()
                closeFragmentDialog()
            }
        }
    }

    private fun closeFragmentDialog() {
        parentFragmentManager.findFragmentByTag("Calendar")?.let {
            (it as CalendarDialogFragment).dismiss()
        }
    }

    companion object{

        lateinit var selectedDate: (Date) -> Unit

        fun setOnAddClickListener(listener: (Date) -> Unit){
            selectedDate = listener
        }

    }

}