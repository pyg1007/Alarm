package kr.ryan.weatheralarm.util

import android.view.View
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kr.ryan.weatheralarm.data.AlarmWithDate

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
        trySend(Unit)
    }

    awaitClose { setOnClickListener(null) }
}

@ExperimentalCoroutinesApi
fun View.onLongSingleClicks() : Flow<Unit> = callbackFlow {

    setOnLongClickListener {
        trySend(Unit)
        true
    }

    awaitClose { setOnLongClickListener(null) }
}