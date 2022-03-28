package kr.ryan.weatheralarm.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.common.GoogleApiAvailability
import kr.ryan.weatheralarm.data.GooglePlayServiceStatus

/**
 * WeatherAlarm
 * Class: Util
 * Created by pyg10.
 * Created On 2022-02-11.
 * Description:
 */

const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/*

    0 = SUCCESS
    1 = MISSING
    2 = SERVICE_VERSION_UPDATE_REQUIRED
    3 = SERVICE_DISABLED
    9 = SERVICE_INVALID
    18 = UPDATING

 */

fun Context.isInstallGooglePlayService(): GooglePlayServiceStatus{
    return when(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)){
        0 -> GooglePlayServiceStatus.SUCCESS
        1 -> GooglePlayServiceStatus.MISSING
        2 -> GooglePlayServiceStatus.SERVICE_VERSION_UPDATE_REQUIRED
        3 -> GooglePlayServiceStatus.SERVICE_DISABLED
        9 -> GooglePlayServiceStatus.SERVICE_INVALID
        18 -> GooglePlayServiceStatus.UPDATING
        else -> throw IllegalStateException("Unknown Status")
    }
}