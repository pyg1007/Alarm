package kr.ryan.alarm.ui.dialog

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.FragmentAlarmDialogBinding
import kr.ryan.alarm.viewmodel.AlarmViewModel
import kr.weather.baseui.BaseDialogFragment

/**
 * Alarm
 * Class: AlarmDialogFragment
 * Created by pyg10.
 * Created On 2021-08-07.
 * Description:
 */
class AlarmDialogFragment: BaseDialogFragment<FragmentAlarmDialogBinding>(R.layout.fragment_alarm_dialog) {

    private val alarmDialogFragment by activityViewModels<AlarmViewModel>()

    init {

        lifecycleScope.launch {
            whenCreated {

                //TODO init Views



            }
        }


    }

}