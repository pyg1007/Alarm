package kr.ryan.alarm.ui

import androidx.activity.viewModels
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.ActivityMainBinding
import kr.ryan.alarm.viewmodel.AlarmViewModel
import kr.weather.baseui.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val alarmViewModel by viewModels<AlarmViewModel>()



}