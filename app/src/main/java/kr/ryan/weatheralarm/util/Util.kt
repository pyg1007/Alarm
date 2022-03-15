package kr.ryan.weatheralarm.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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