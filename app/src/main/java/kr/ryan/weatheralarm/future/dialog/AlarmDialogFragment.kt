package kr.ryan.weatheralarm.future.dialog

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.adapter.AlarmAdapter
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.databinding.DialogAlarmBinding
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.util.Route.EDIT_MODE
import kr.ryan.weatheralarm.viewModel.AlarmEditViewModel
import kr.ryan.weatheralarm.viewModel.AlarmViewModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-11-18.
 * Description:
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AlarmDialogFragment : BaseDialogFragment<DialogAlarmBinding>(R.layout.dialog_alarm) {

    private val editViewModel by viewModels<AlarmEditViewModel>()
    private val alarmViewModel by viewModels<AlarmViewModel>()

    private var alarm: Alarm? = null

    @Inject
    lateinit var adapter: AlarmAdapter

    private var preHour = 0
    private var preMin = 0

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
                requireActivity().dialogFragmentResize(this@AlarmDialogFragment, 0.8f, 0.8f)
            }

            whenCreated {
                initAlarm()
            }

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    observeUiState()
                }
                launch {
                    disMissFocusEditText()
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
    }

    private fun initAlarm() {
        arguments?.let {
            alarm = it.getParcelable("Alarm")
        }
    }

    private fun initBinding() {
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

    private fun initViewModel() {
        val alarmWithDate: AlarmWithDate? = arguments?.getParcelable("alarmWithDate")
        alarmWithDate?.let {
            val date = it.alarmDate[0].date
            preHour = date.getCurrentHour()
            preMin = date.getCurrentMin()
            editViewModel.run {
                changeYear(date.getCurrentYear())
                changeMonth(date.getCurrentMonth())
                changeDate(date.getCurrentDate())
                changeHour(preHour)
                changeMinute(preMin)
                changeTitle(it.alarm.title)
                if (it.alarm.isRepeat)
                    changeDays(it.alarmDate)
                changeAlarmWithDate(it)
                changeMode(Mode(EDIT_MODE))
            }
        } ?: run {
            val date = Date()
            preHour = date.getCurrentHour()
            preMin = date.getCurrentMin()
            editViewModel.run {
                changeYear(date.getCurrentYear())
                changeMonth(date.getCurrentMonth())
                changeDate(date.getCurrentDate())
                changeHour(preHour)
                changeMinute(preMin)
            }
        }
    }

    private fun changeTimePicker() {
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

    private suspend fun disMissFocusEditText(){
        binding.rootLayout.onSingleClicks().onEach {
            binding.etAlarmTitle.clearFocus()
            requireContext().hideKeyboard(binding.etAlarmTitle)
        }.stateIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun observeUiState() {
        alarmViewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    if (it.route == Route.CANCEL) {
                        cancelEvent()
                        alarmViewModel.sendEvent(UiEvent.PopUpStack)
                    } else if (it.route == Route.SAVE) {
                        editViewModel.onActive({ alarmWithDate, isExist ->

                            Timber.d("SAVE")

                            if (isExist){
                                saveEvent("이미 등록되어있는 날짜입니다.")
                                alarmViewModel.sendEvent(UiEvent.PopUpStack)
                            }else {

                                if (Date() < alarmWithDate.alarmDate.sortedBy { date -> date.date }[0].date) {

                                    val alarmManager =
                                        requireContext().getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                                    if (requireContext().isRegisterAlarm(alarmWithDate)) {
                                        alarmManager?.cancelAlarm(requireContext(), alarmWithDate)
                                    }

                                    alarmManager?.registerAlarm(requireContext(), alarmWithDate)

                                    saveEvent(null)
                                    alarmViewModel.sendEvent(UiEvent.PopUpStack)
                                } else {
                                    alarmViewModel.sendEvent(UiEvent.ShowSnackBar("설정한 시간이 현재 시각 이전입니다."))
                                }
                            }


                        }, { throwable ->
                            Timber.d("failure -> $throwable")
                        })
                    }
                }
                is UiEvent.ShowSnackBar -> {
                    Timber.d("show SnackBar ${it.message}")
                    Snackbar.make(binding.rootLayout, it.message, Snackbar.LENGTH_SHORT).show()
                }
                is UiEvent.PopUpStack -> {
                    dismiss()
                }
            }
        }
    }
}