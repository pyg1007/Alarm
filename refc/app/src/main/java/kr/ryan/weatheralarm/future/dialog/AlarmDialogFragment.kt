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
import kr.ryan.weatheralarm.util.dialogFragmentResize
import kr.ryan.weatheralarm.util.getCurrentHour
import kr.ryan.weatheralarm.util.getCurrentMin
import kr.ryan.weatheralarm.viewModel.AlarmEditViewModel
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

    private val viewModel by viewModels<AlarmEditViewModel>()

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
                    onClickCancelBtn()
                }

                launch {
                    onClickSaveBtn()
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

    private fun initAlarm(){
        arguments?.let {
            alarm = it.getParcelable("Alarm")
        }
    }

    private fun initBinding(){
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AlarmDialogFragment.viewModel

            includeDay.apply {
                viewModel = this@AlarmDialogFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }
    }

    private fun initViewModel(){
        alarm?.let {

            it.days[0].run { // 시간 초기 설정 (alarm 이 null 이 아니라는 소리는 edit 상태이기 때문)
                viewModel.changeHour(getCurrentHour())
                viewModel.changeMinute(getCurrentMin())
            }

            viewModel.changeTitle(it.title ?: "")
            viewModel.changeDates(it.days)

        }
    }

    private fun changeTimePicker(){
        binding.timePick.setOnTimeChangedListener { timePicker, hour, min ->

        }
    }

    private suspend fun onClickCancelBtn() {
        viewModel.cancelEvent.collect {
            if (it){
                cancelEvent()
                viewModel.initCancelEvent()
                dismiss()
            }
        }
    }

    private suspend fun onClickSaveBtn() {
        viewModel.saveEvent.collect {
            if (it){
                alarm?.let{ alarm ->
                    viewModel.insertAlarm(alarm)
                }
                saveEvent()
                viewModel.initSaveEvent()
                dismiss()
            }
        }
    }
}