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
    val index: Long = 0, val pendingId: Int, val title: String?, val isRepeat: Boolean, val onOff: Boolean
) : Parcelable

@Parcelize
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
    val index: Long = 0,
    var alarmId: Long? = null, val date: Date
): Parcelable

@Parcelize
data class AlarmWithDate(
    @Embedded val alarm: Alarm,
    @Relation(
        parentColumn = "index",
        entityColumn = "alarmId"
    )
    val alarmDate: List<AlarmDate>
): Parcelable