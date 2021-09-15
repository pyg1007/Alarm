package kr.ryan.alarm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Alarm
 * Class: Alarm
 * Created by pyg10.
 * Created On 2021-08-04.
 * Description:
 */
@Parcelize
@Entity(tableName = "Alarm_table")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0,
    var isSingleAlarm: Boolean,
    var title: String,
    var alarmTimeList: List<Date>,
    var alarmOnOff: Boolean
): Parcelable