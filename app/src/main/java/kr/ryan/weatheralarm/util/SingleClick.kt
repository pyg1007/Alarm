package kr.ryan.weatheralarm.util

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * WeatherAlarm
 * Class: SingleClick
 * Created by pyg10.
 * Created On 2022-02-21.
 * Description:
 */

@ExperimentalCoroutinesApi
fun View.onSingleClicks() : Flow<Unit> = callbackFlow {

    setOnClickListener {
        trySend(Unit).isSuccess
    }

    awaitClose { setOnClickListener(null) }
}

@ExperimentalCoroutinesApi
fun View.onLongSingleClicks() : Flow<Unit> = callbackFlow {

    setOnLongClickListener {
        trySend(Unit).isSuccess
        true
    }

    awaitClose { setOnLongClickListener(null) }
}