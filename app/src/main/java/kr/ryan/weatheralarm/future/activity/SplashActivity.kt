package kr.ryan.weatheralarm.future.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.permissionmodule.requestPermission
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.util.getCurrentLatXLngY
import kr.ryan.weatheralarm.viewModel.WeatherViewModel
import timber.log.Timber

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val weatherViewModel by viewModels<WeatherViewModel>()

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            weatherViewModel.weather.collect {
                if (it == null) {
                    requestPermission({
                        this@SplashActivity.getCurrentLatXLngY()?.let { latXLngY ->
                            Timber.d("location x = ${latXLngY.x} y = ${latXLngY.y} lat = ${latXLngY.lat} lon = ${latXLngY.lng}")
                            routeNextActivity()
                        } ?: run {
                            Timber.d("location Error")
                            routeNextActivity()
                        }
                    },{
                        Timber.d("denied")
                    }, *permissions)
                }else{
                    Timber.d("not null $it")
                    routeNextActivity()
                }
            }
        }

    }

    private fun routeNextActivity() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            Intent(this@SplashActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}