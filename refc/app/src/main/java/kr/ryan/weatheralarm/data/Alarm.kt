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
    var index: Long? = null, val pendingId: Int, var title: String?, var isRepeat: Boolean, var onOff: Boolean
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
    val index: Long? = null,
    var alarmIndex: Long? = null, var date: Date
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