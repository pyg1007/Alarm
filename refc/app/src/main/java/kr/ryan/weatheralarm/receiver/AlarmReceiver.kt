package kr.ryan.weatheralarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.findFastDate
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * WeatherAlarm
 * Class: AlarmReceiver
 * Created by pyg10.
 * Created On 2022-01-21.
 * Description:
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var updateUseCase: AlarmUpdateUseCase

    @Inject
    lateinit var selectUseCase: AlarmSelectUseCase

    override fun onReceive(context: Context?, intent: Intent?) {

        CoroutineScope(Dispatchers.IO).launch {
            intent?.let { it ->



            }
        }
        Timber.d("Alarm Receiver Active")
    }

}