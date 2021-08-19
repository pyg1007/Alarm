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
    var alarmTimeList: List<Date>,
    var alarmOnOff: Boolean
) {
    @PrimaryKey
    var index: Int = 0
}