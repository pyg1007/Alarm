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
import kr.ryan.weatheralarm.usecase.WeatherSelectUseCase
import kr.ryan.weatheralarm.util.*
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

    @Inject
    lateinit var selectWeather: WeatherSelectUseCase

    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        CoroutineScope(Dispatchers.IO).launch {
            intent?.let { it ->

                it.getParcelableExtra<AlarmDate>("alarm")?.let { alarmDate ->

                    val alarmInfo = selectUseCase.selectAlarmInfo(alarmDate.alarmIndex ?: -1)

                    if (!alarmInfo.isRepeat){ // 요일

                        alarmInfo.onOff = false
                        updateUseCase.updateAlarmInfo(alarmInfo)

                    }else{ // 날짜

                        val alarmList = selectUseCase.selectAlarmDate(alarmInfo.index ?: -1)

                        if (!alarmList.isNullOrEmpty()){

                            var index : Int = -1

                            for (i in alarmList.indices){
                                if (alarmList[i].date == alarmDate.date) {
                                    index = i
                                    break
                                }
                            }

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

                        }
                    }

                }

                selectWeather.selectWeatherInfo().collect {
                    Timber.d("$it")
                    it?.let { internalWeather ->

                        context?.createNotification(internalWeather)

                    }
                }

            }
        }
    }

}