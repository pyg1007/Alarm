package kr.ryan.weatheralarm.future.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.DialogAlarmBinding
import kr.ryan.weatheralarm.util.dialogFragmentResize
import kr.ryan.weatheralarm.viewModel.AlarmEditViewModel

/**
 * WeatherAlarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-11-18.
 * Description:
 */
class AlarmDialogFragment : BaseDialogFragment<DialogAlarmBinding>(R.layout.dialog_alarm) {

    private val alarmEditViewModel by viewModels<AlarmEditViewModel>()
    private var alarmData: Alarm? = null

    init {
        lifecycleScope.launch {
            whenResumed {
                requireActivity().dialogFragmentResize(this@AlarmDialogFragment, 0.8f, 0.8f)
            }

            whenCreated {
                getAlarmData()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getAlarmData() {
        alarmData = arguments?.getParcelable("alarm")
    }

    private fun initViewModel() {
        alarmData?.let {
            it.title
        }
    }
}