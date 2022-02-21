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
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location == null)
                location = locationManager.getLocationWithGPSListener()

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

@SuppressLint("MissingPermission")
private suspend fun LocationManager.getLocationWithGPSListener() = suspendCancellableCoroutine<Location> {

    val gpsListener = object : LocationListener{
        override fun onLocationChanged(p0: Location) {
            it.resume(p0)
            removeUpdates(this)
        }

        override fun onLocationChanged(locations: MutableList<Location>) {
            super.onLocationChanged(locations)
        }

        override fun onFlushComplete(requestCode: Int) {
            super.onFlushComplete(requestCode)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }

    requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, gpsListener)

    it.invokeOnCancellation { removeUpdates(gpsListener) }

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