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

    companion object{

        private lateinit var cancelEvent: () -> Unit
        private lateinit var saveEvent: (Alarm) -> Unit

        fun setOnCancelEvent(onClick : () -> Unit){
            cancelEvent = onClick
        }

        fun setOnSaveEvent(onClick: (Alarm) -> Unit){
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

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    onClickCancelBtn()
                }

                launch {
                    onClickSaveBtn()
                }

                launch {
                    observeStatus()
                }
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
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AlarmDialogFragment.viewModel

            includeDay.apply {
                viewModel = this@AlarmDialogFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }
    }

    private suspend fun observeStatus() {
        viewModel.dayStatus.collect {
            Timber.d("$it")
        }
    }

    private suspend fun onClickCancelBtn() {
        viewModel.cancelEvent.collect {
            if (it){
                cancelEvent
                viewModel.initCancelEvent()
                dismiss()
            }
        }
    }

    private suspend fun onClickSaveBtn() {
        viewModel.saveEvent.collect {
            if (it){
                Timber.d("save")
                viewModel.initSaveEvent()
                dismiss()
            }
        }
    }
}