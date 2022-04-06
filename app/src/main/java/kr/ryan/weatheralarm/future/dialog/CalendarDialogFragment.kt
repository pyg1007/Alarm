package kr.ryan.weatheralarm.future.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.databinding.DialogCalendarBinding
import kr.ryan.weatheralarm.util.dialogFragmentResize
import kr.ryan.weatheralarm.viewModel.AlarmViewModel

/**
 * WeatherAlarm
 * Class: CalendarDialogFragment
 * Created by pyg10.
 * Created On 2022-04-06.
 * Description:
 */
class CalendarDialogFragment : BaseDialogFragment<DialogCalendarBinding>(R.layout.dialog_calendar) {

    private val alarmViewModel by viewModels<AlarmViewModel>()

    companion object {

        private lateinit var cancelEvent: () -> Unit
        private lateinit var saveEvent: (String?) -> Unit

        fun setOnCancelEvent(onClick: () -> Unit) {
            cancelEvent = onClick
        }

        fun setOnSaveEvent(onClick: (String?) -> Unit) {
            saveEvent = onClick
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewLifecycleOwner.lifecycleScope.launch {

            whenResumed {
                requireActivity().dialogFragmentResize(this@CalendarDialogFragment, 0.8f, 0.8f)
            }

        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()

    }

    private fun initBinding(){
        binding.apply {
            alarmViewModel = alarmViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}