package kr.ryan.weatheralarm.future.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
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

    private lateinit var settingsLauncher: ActivityResultLauncher<Intent>

    init {

        lifecycleScope.launch {

            whenStarted {

            }

            whenCreated {
                initLocationLauncher()

                CoroutineScope(Dispatchers.Main).launch {

                }
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

    }

    private fun initLocationLauncher(){
        settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                CoroutineScope(Dispatchers.Main).launch {
                    observeWeather()
                }
            }
        }
    }

    private fun routeSettingsLocation(){
        Intent(Settings.ACTION_LOCALE_SETTINGS).also {
            settingsLauncher.launch(it)
        }
    }

    private suspend fun observeWeather(){
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