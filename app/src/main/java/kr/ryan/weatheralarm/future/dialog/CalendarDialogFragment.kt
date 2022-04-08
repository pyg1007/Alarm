package kr.ryan.weatheralarm.future.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenResumed
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.databinding.DialogCalendarBinding
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import java.util.*

/**
 * WeatherAlarm
 * Class: CalendarDialogFragment
 * Created by pyg10.
 * Created On 2022-04-06.
 * Description:
 */
@AndroidEntryPoint
class CalendarDialogFragment : BaseDialogFragment<DialogCalendarBinding>(R.layout.dialog_calendar) {

    private val alarmViewModel by viewModels<AlarmViewModel>()

    private var year = Date().getCurrentYear()
    private var month = Date().getCurrentMonth()
    private var day = Date().getCurrentDate()

    companion object {

        private lateinit var cancelEvent: () -> Unit
        private lateinit var saveEvent: (Int, Int, Int) -> Unit

        fun setOnCancelEvent(onClick: () -> Unit) {
            cancelEvent = onClick
        }

        fun setOnSaveEvent(onClick: (Int, Int, Int) -> Unit) {
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
                requireActivity().dialogFragmentResize(this@CalendarDialogFragment, 0.8f, 0.5f)
            }

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                Timber.d("START")
                launch {
                    observeUiState()
                }
            }

        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initCalendarView()
        dateChangeListener()
    }

    private fun initBinding() {
        binding.apply {
            alarmViewModel = alarmViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initCalendarView() {
        binding.calendar.minDate = Date().time
    }

    private fun dateChangeListener() {
        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            Timber.d("year -> $year month -> ${month + 1} day -> $day")
            this.year = year
            this.month = month + 1
            this.day = day
        }
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            Timber.d("Active UiState -> $it")
            when (it) {
                is UiEvent.Navigate -> {
                    when (it.route) {
                        Route.CANCEL -> {
                            Timber.d("Cancel Clicked")
                            cancelEvent()
                            alarmViewModel.sendEvent(UiEvent.PopUpStack)
                        }
                        Route.SAVE -> {
                            Timber.d("Save Clicked")
                            saveEvent(year, month, day)
                            alarmViewModel.sendEvent(UiEvent.PopUpStack)
                        }
                        else -> {

                        }
                    }
                }
                is UiEvent.PopUpStack -> {
                    dismiss()
                }
                else -> {

                }
            }
        }
    }
}