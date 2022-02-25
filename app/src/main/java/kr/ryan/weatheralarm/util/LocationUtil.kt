package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.provider.Settings
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
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

@SuppressLint("MissingPermission")
suspend fun lastLocationListener(context: Context) = suspendCancellableCoroutine<Location> { result ->

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
        if (location == null){

            val request = LocationRequest.create().also {
                it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                it.interval = 5 * 1000
            }

            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {

                    for (locations in locationResult.locations){
                        if (locations != null){
                            result.resume(locations)
                        }
                    }

                }
            }

            fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            result.invokeOnCancellation { fusedLocationProviderClient.removeLocationUpdates(locationCallback) }

        }else{
            result.resume(location)
        }
    }


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