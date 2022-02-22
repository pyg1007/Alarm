package kr.ryan.weatheralarm.future.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.permissionmodule.requestPermission
import kr.ryan.weatheralarm.BuildConfig
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.util.*
import kr.ryan.weatheralarm.viewModel.WeatherViewModel
import timber.log.Timber
import java.net.URLDecoder
import java.util.*

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

            whenCreated {
                initLocationLauncher()

                CoroutineScope(Dispatchers.Main).launch {

                }
            }

            repeatOnLifecycle(Lifecycle.State.STARTED){
                observeWeather()
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

    private fun showDialog(){
        val dialog: AlertDialog = this@SplashActivity.let {
            val builder = AlertDialog.Builder(it).apply{
                setPositiveButton("이동") { dialog, _ ->
                    routeSettingsLocation()
                    dialog.dismiss()
                }
                setNegativeButton("닫기") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                setMessage("")
            }
            builder.create()
        }
        dialog.show()
    }

    private fun routeSettingsLocation(){
        Intent(Settings.ACTION_LOCALE_SETTINGS).also {
            settingsLauncher.launch(it)
        }
    }

    private suspend fun observeWeather(){
        Timber.d("observe Weather Active")
        weatherViewModel.weather.collect {
            if (it == null) {
                requestPermission({
                    if (this@SplashActivity.isEnableLocationSystem()) {
                        Timber.d("enable Location System")
                        this@SplashActivity.getCurrentLatXLngY()?.let { latXLngY ->
                            callApi(latXLngY)
                            Timber.d("location x = ${latXLngY.x} y = ${latXLngY.y} lat = ${latXLngY.lat} lon = ${latXLngY.lng}")
                        } ?: run {
                            Timber.d("location Error")
                            routeNextActivity()
                        }
                    }else{
                        showDialog()
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

    private fun callApi(latXLngY: CalculatorLatitudeAndLongitude.LatXLngY) {
        val standardDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 2)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
        }
        val baseDate = Calendar.getInstance()
        if(baseDate.before(standardDate))
            baseDate.add(Calendar.DAY_OF_MONTH, -1)

        Timber.d("time -> ${baseDate.time.convertDateWithDayToString()} ${baseDate.time.convertTime()}")

        weatherViewModel.weatherInfo(hashMapOf(
            "serviceKey" to URLDecoder.decode(BuildConfig.weather_api_key, "UTF-8"),
            "nx" to latXLngY.x.toInt().toString(),
            "ny" to latXLngY.y.toInt().toString(),
            "dataType" to "JSON",
            "base_date" to baseDate.time.convertBaseDate(),
            "base_time" to baseDate.apply {
                set(Calendar.HOUR_OF_DAY, 2)
                set(Calendar.MINUTE, 0)
            }.time.convertBaseTime()
        )) {
            routeNextActivity()
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