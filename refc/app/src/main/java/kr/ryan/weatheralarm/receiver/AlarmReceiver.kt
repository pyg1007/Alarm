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
                it.getParcelableExtra<AlarmWithDate>("alarm")?.let { alarmWithDate ->
                    if (!alarmWithDate.alarm.isRepeat) {
                        val alarm = alarmWithDate.alarm
                        val alarmId = alarmWithDate.alarmDate[0].alarmId
                        alarm.apply {
                            index = alarmId
                            onOff = false
                        }
                        updateUseCase.updateAlarmInfo(alarm)
                    }else{
                        Timber.d("alarmDate -> ${alarmWithDate.alarmDate}")
                        Timber.d("fast alarmDate -> ${alarmWithDate.findFastDate()}")
                        Calendar.getInstance().apply {
                            time = alarmWithDate.alarmDate.find { alarmDate ->
                                alarmDate.date == alarmWithDate.findFastDate()
                            }?.date ?: Date()
                        }.add(Calendar.DAY_OF_MONTH, 7)

                        Timber.d("result alarmDate -> ${alarmWithDate.alarmDate}")
                    }
                }
            }
        }
        Timber.d("Alarm Receiver Active")
    }

}