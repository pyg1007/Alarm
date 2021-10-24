package kr.ryan.weatheralarm.future.activity

import androidx.activity.viewModels
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kr.ryan.baseui.BaseActivity
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.Alarm
import kr.ryan.weatheralarm.databinding.ActivityMainBinding
import kr.ryan.weatheralarm.viewModel.AlarmViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {


    private val alarmViewModel by viewModels<AlarmViewModel>()

    init {

        lifecycleScope.launch {

            whenStarted {

            }

            whenCreated {

            }

            whenResumed {

            }

            repeatOnLifecycle(Lifecycle.State.STARTED){

            }

        }


    }

}