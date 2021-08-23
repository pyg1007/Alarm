package kr.ryan.alarm.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CalendarView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.FragmentCalendarDialogBinding
import kr.ryan.alarm.utility.dialogFragmentResize
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
    BaseDialogFragment<FragmentCalendarDialogBinding>(R.layout.fragment_calendar_dialog),
    CalendarView.OnDateChangeListener {

    private var selectDate = Date()

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
        initCalendarView()
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

    }

    private fun initCalendarView() {
        binding.calendar.setOnDateChangeListener(this)
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        selectDate = Calendar.getInstance().apply {
            time = selectDate
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.time
    }

    private fun closeFragmentDialog() {
        parentFragmentManager.findFragmentByTag("Calendar")?.let {
            (it as CalendarDialogFragment).dismiss()
        }
    }

}