package kr.ryan.weatheralarm.future.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.DialogAlarmBinding
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.viewModel.AlarmEditViewModel
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import java.util.*

/**
 * WeatherAlarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-11-18.
 * Description:
 */
@AndroidEntryPoint
class AlarmDialogFragment : BaseDialogFragment<DialogAlarmBinding>(R.layout.dialog_alarm) {

    private val editViewModel by viewModels<AlarmEditViewModel>()
    private val alarmViewModel by viewModels<AlarmViewModel>()

    private var alarm: Alarm? = null

    private var preHour = 0
    private var preMin = 0

    companion object{

        private lateinit var cancelEvent: () -> Unit
        private lateinit var saveEvent: () -> Unit

        fun setOnCancelEvent(onClick : () -> Unit){
            cancelEvent = onClick
        }

        fun setOnSaveEvent(onClick: () -> Unit){
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
                requireActivity().dialogFragmentResize(this@AlarmDialogFragment, 0.8f, 0.8f)
            }

            whenCreated {
                initAlarm()
            }

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
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
        initViewModel()
        changeTimePicker()
        test()
    }

    private fun initAlarm(){
        arguments?.let {
            alarm = it.getParcelable("Alarm")
        }
    }

    private fun initBinding(){
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            alarmViewModel = this@AlarmDialogFragment.alarmViewModel
            editViewModel = this@AlarmDialogFragment.editViewModel

            includeDay.apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = editViewModel
            }
        }
    }

    private fun initViewModel(){
        val date = Date()
        preHour = date.getCurrentHour()
        preMin = date.getCurrentMin()
        editViewModel.changeHour(preHour)
        editViewModel.changeMinute(preMin)
    }

    private fun changeTimePicker(){
        binding.timePick.setOnTimeChangedListener { _, hour, min ->
            if (preHour != hour) {
                editViewModel.changeHour(hour)
                preHour = hour
            }
            if (preMin != min) {
                editViewModel.changeMinute(min)
                preMin = min
            }
        }
    }

    private fun test(){
        editViewModel.selectedHour.observe(viewLifecycleOwner, Observer {
            Timber.d("hour -> $it")
        })

        editViewModel.selectedMinute.observe(viewLifecycleOwner, Observer {
            Timber.d("minute -> $it")
        })
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            when(it){
                is UiEvent.Navigate -> {
                    if (it.route == Route.CANCEL) {
                        cancelEvent()
                        alarmViewModel.sendChannelEvent(UiEvent.PopUpStack)
                    }
                    else if(it.route == Route.SAVE) {
                        saveEvent()
                        alarmViewModel.sendChannelEvent(UiEvent.PopUpStack)
                    }
                }
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.PopUpStack -> {
                    dismiss()
                }
            }
        }
    }
}