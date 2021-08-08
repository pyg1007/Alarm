package kr.ryan.alarm.ui.activity

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.launch
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.ActivitySplashBinding
import kr.weather.baseui.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {


    init {

        lifecycleScope.launch {

            whenStarted {



            }

        }

    }

}