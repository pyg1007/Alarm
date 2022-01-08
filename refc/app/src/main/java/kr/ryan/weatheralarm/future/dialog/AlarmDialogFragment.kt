package kr.ryan.weatheralarm.future.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
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

//                launch {
//                    onClickSaveBtn()
//                }


            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        changeTimePicker()

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
        }
    }

    private fun changeTimePicker(){
        binding.timePick.setOnTimeChangedListener { timePicker, hour, min ->

        }
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            when(it){
                is UiEvent.Navigate -> {
                    if (it.route == Route.CANCEL) {
                        cancelEvent()
                    }
                    else if(it.route == Route.SAVE) {
                        saveEvent()
                    }
                    dismiss()
                }
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.PopUpStack -> {

                }
            }
        }
    }
}