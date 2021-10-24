package kr.ryan.weatheralarm.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    var time: Long?, var days: List<Date>?, var switch: Boolean
) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var index: Long = 0


}
