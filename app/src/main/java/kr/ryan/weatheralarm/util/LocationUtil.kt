package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.provider.Settings
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.LatXLngY
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.TO_GRID
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
            var location = lastLocationListener(this@getCurrentLatXLngY)
            if (location == null)
                location = lastLocationListener(this@getCurrentLatXLngY)

            CalculatorLatitudeAndLongitude.convertGRIDTOGPS(
                TO_GRID,
                location!!.latitude,
                location.longitude
            )
        }.onFailure {
            Timber.d("Location Error => $it")
        }.getOrNull()
    }.await()
}

@SuppressLint("MissingPermission")
suspend fun lastLocationListener(context: Context) =
    suspendCancellableCoroutine<Location?> { result ->

        runCatching {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location == null) {

                    val request = LocationRequest.create().also {
                        it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        it.interval = 5 * 1000
                    }

                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {

                            for (locations in locationResult.locations) {
                                if (locations != null && result.isActive) {
                                    result.resume(locations)
                                }
                            }

                        }
                    }

                    fusedLocationProviderClient.requestLocationUpdates(
                        request,
                        locationCallback,
                        Looper.getMainLooper()
                    )

                    result.invokeOnCancellation {
                        fusedLocationProviderClient.removeLocationUpdates(
                            locationCallback
                        )
                    }

                } else {
                    if (result.isActive)
                        result.resume(location)
                }
            }
        }.onFailure {
            Timber.d("Failure -> $it")
        }
    }

fun Context.isEnableLocationSystem(): Boolean {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        locationManager?.isLocationEnabled!!
    } else {
        val mode = Settings.Secure.getInt(
            contentResolver,
            Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF
        )
        mode != Settings.Secure.LOCATION_MODE_OFF
    }

}

fun Context.currentLocation(nx: Int, ny: Int): String? {
    return runCatching {
        val geocoder = Geocoder(this)
        val address = geocoder.getFromLocation(nx.toDouble(), ny.toDouble(), 10)

        when {
            address.isNullOrEmpty() || address.size == 0 -> null
            else -> {
                when {
                    address[0].adminArea == null -> address[0].featureName
                    address[0].thoroughfare == null -> address[0].adminArea
                    else -> address[0].adminArea.plus(" " + address[0].thoroughfare)
                }
            }
        }

    }.onFailure {
        Timber.d("GeoCoder Error")
    }.getOrNull()
}