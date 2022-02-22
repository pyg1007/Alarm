package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * WeatherAlarm
 * Class: Location
 * Created by pyg10.
 * Created On 2022-02-21.
 * Description:
 */
@SuppressLint("MissingPermission")
object Location{

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

}