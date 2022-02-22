package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import kotlinx.coroutines.*
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.TO_GRID
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.LatXLngY
import kr.ryan.weatheralarm.util.Location.lastLocationListener
import timber.log.Timber
import kotlin.coroutines.resume

/**
 * WeatherAlarm
 * Class: LocationUtil
 * Created by pyg10.
 * Created On 2022-02-17.
 * Description:
 */

@SuppressLint("MissingPermission")
suspend fun Context.getCurrentLatXLngY(): LatXLngY? {
    return CoroutineScope(Dispatchers.Main).async {
        return@async runCatching {
            val location = lastLocationListener(this@getCurrentLatXLngY)

            CalculatorLatitudeAndLongitude.convertGRIDTOGPS(
                TO_GRID,
                location.latitude,
                location.longitude
            )
        }.onFailure {
            Timber.d("Location Error => $it")
        }.getOrNull()
    }.await()
}

fun Context.isEnableLocationSystem(): Boolean {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        locationManager?.isLocationEnabled!!
    }else{
        val mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
        mode != Settings.Secure.LOCATION_MODE_OFF
    }

}