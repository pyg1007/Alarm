package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.TO_GRID
import kr.ryan.weatheralarm.util.CalculatorLatitudeAndLongitude.LatXLngY
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: LocationUtil
 * Created by pyg10.
 * Created On 2022-02-17.
 * Description:
 */

@SuppressLint("MissingPermission")
suspend fun Context.getCurrentLatXLngY(): LatXLngY? {
    return CoroutineScope(Dispatchers.Default).async {
        return@async runCatching {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null)
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if (location == null){

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object : LocationListener{
                    override fun onLocationChanged(p0: Location) {
                        location = p0
                        locationManager.removeUpdates(this)
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
                })

            }


            CalculatorLatitudeAndLongitude.convertGRIDTOGPS(
                TO_GRID,
                location!!.latitude,
                location!!.longitude
            )
        }.onFailure {
            Timber.d("Location Error => $it")
        }.getOrNull()
    }.await()
}