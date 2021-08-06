package kr.ryan.alarm.utility

import java.text.SimpleDateFormat
import java.util.*

/**
 * Alarm
 * Class: RemainTime
 * Created by pyg10.
 * Created On 2021-08-06.
 * Description:
 */


fun Date.fastAlarm(locale: Locale = Locale.getDefault()): String{

    val simpleDateFormat: SimpleDateFormat

    return when{
        1000L * 60 * 24 * 99 < time ->{
            simpleDateFormat = SimpleDateFormat("MM월 뒤에 알람이 울립니다.", locale)
            simpleDateFormat.format(this)
        }
        1000L * 60 * 24 * 10 < time -> {
            simpleDateFormat = SimpleDateFormat("dd일 뒤에 알람이 울립니다.", locale)
            simpleDateFormat.format(this)
        }

        1000L * 60 * 24 < time -> {
            simpleDateFormat = SimpleDateFormat("d일 HH시간 mm분 ss초 뒤에 알람이 울립니다.", locale)
            simpleDateFormat.format(this)
        }

        1000L * 60 < time -> {
            simpleDateFormat = SimpleDateFormat("mm분 ss초 뒤에 알람이 울립니다.", locale)
            simpleDateFormat.format(this)
        }
        else -> {
            simpleDateFormat = SimpleDateFormat("잠시후에 알람이 울립니다.", locale)
            simpleDateFormat.format(this)
        }

    }
}