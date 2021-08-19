package kr.ryan.alarm.ui.activity

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.*
import kr.ryan.alarm.R
import kr.ryan.alarm.databinding.ActivitySplashBinding
import kr.ryan.baseui.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    init {

        lifecycleScope.launch {

            whenStarted {
                //TODO BackGround Location With ForeGroundService Permission Request

            }

            whenCreated {

                nextActivity()

            }

        }

    }

    private suspend fun nextActivity() = withContext(Dispatchers.Main) {
        delay(3 * 1000L)

        Intent(this@SplashActivity, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

}