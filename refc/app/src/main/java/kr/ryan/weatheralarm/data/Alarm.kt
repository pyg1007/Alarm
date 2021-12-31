package kr.ryan.weatheralarm.data

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * WeatherAlarm
 * Class: alarm
 * Created by pyg10.
 * Created On 2021-10-24.
 * Description:
 */
@Parcelize
@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val index: Long = 0, val title: String?, val onOff: Boolean
) : Parcelable

@Entity(
    tableName = "date",
    foreignKeys = [
        ForeignKey(
            entity = Alarm::class,
            parentColumns = arrayOf("index"),
            childColumns = arrayOf("alarmId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AlarmDate(
    @PrimaryKey(autoGenerate = true)
    val index: Long = 0, val alarmId: Long, val date: Date
)

data class AlarmWithDate(
    @Embedded val alarm: Alarm,
    @Relation(
        parentColumn = "index",
        entityColumn = "alarmId"
    )
    val alarmDate: List<AlarmDate>
)