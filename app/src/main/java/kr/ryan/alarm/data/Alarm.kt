package kr.ryan.alarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Alarm
 * Class: Alarm
 * Created by pyg10.
 * Created On 2021-08-04.
 * Description:
 */
@Entity(tableName = "Alarm_table")
data class Alarm(
    var isSingleAlarm: Boolean,
    var title: String?,
    var alarmTimeList: List<Date>,
    var alarmOnOff: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0
}