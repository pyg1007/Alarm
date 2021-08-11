package kr.ryan.alarm.utility

import android.content.Context
import android.widget.Toast

/**
 * Alarm
 * Class: Toast
 * Created by pyg10.
 * Created On 2021-08-12.
 * Description:
 */


fun Context.showShortToast(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}