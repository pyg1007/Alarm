package kr.ryan.weatheralarm.receiver

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ryan.weatheralarm.data.AlarmDate
import kr.ryan.weatheralarm.data.AlarmWithDate
import kr.ryan.weatheralarm.usecase.AlarmSelectUseCase
import kr.ryan.weatheralarm.usecase.AlarmUpdateUseCase
import kr.ryan.weatheralarm.util.cancelAlarm
import kr.ryan.weatheralarm.util.findFastDate
import kr.ryan.weatheralarm.util.isRegisterAlarm
import kr.ryan.weatheralarm.util.registerAlarm
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

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        CoroutineScope(Dispatchers.IO).launch {
            intent?.let { it ->

                Timber.d("Alarm Receiver Intent Not Null")

                it.getParcelableExtra<AlarmDate>("alarm")?.let { alarmDate ->

                    Timber.d("Alarm Receiver Parcel Data Not null")
                    val alarmInfo = selectUseCase.selectAlarmInfo(alarmDate.alarmIndex ?: -1)

                    Timber.d("Alarm Receiver receive Alarm Info $alarmInfo")
                    if (!alarmInfo.isRepeat){ // 요일

                        alarmInfo.onOff = false
                        updateUseCase.updateAlarmInfo(alarmInfo)

                        Timber.d("Alarm Receiver alarm Info Update")

                    }else{ // 날짜

                        val alarmList = selectUseCase.selectAlarmDate(alarmInfo.index ?: -1)

                        Timber.d("Alarm Receiver Alarm List $alarmList")

                        if (!alarmList.isNullOrEmpty()){

                            var index : Int = -1

                            for (i in alarmList.indices){
                                if (alarmList[i].date == alarmDate.date) {
                                    index = i
                                    break
                                }
                            }

                            Timber.d("index -> $index")
                            val nextTime = Calendar.getInstance().apply {
                                time = alarmDate.date
                            }

                            nextTime.add(Calendar.DAY_OF_MONTH, 7)
                            alarmList[index].date = nextTime.time

                            updateUseCase.updateAlarmDate(alarmList)

                            val alarm = selectUseCase.selectAlarmWithDate(alarmDate.alarmIndex ?: -1)
                            context?.let {
                                if (it.isRegisterAlarm(alarm))
                                    alarmManager?.cancelAlarm(it, alarm)

                                alarmManager?.registerAlarm(it, alarm)
                            }


                            Timber.d("Alarm Receiver alarm List Update")
                        }
                    }

                }

            }
        }
        Timber.d("Alarm Receiver Active")
    }

}