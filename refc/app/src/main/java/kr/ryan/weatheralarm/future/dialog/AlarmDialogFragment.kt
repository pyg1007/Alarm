package kr.ryan.weatheralarm.future.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.ryan.baseui.BaseDialogFragment
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.databinding.DialogAlarmBinding
import kr.ryan.weatheralarm.viewModel.AlarmEditViewModel

/**
 * WeatherAlarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-11-18.
 * Description:
 */
@AndroidEntryPoint
class AlarmDialogFragment : BaseDialogFragment<DialogAlarmBinding>(R.layout.dialog_alarm) {

    private val alarmEditViewModel by viewModels<AlarmEditViewModel>()



}