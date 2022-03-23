package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kr.ryan.weatheralarm.R
import kr.ryan.weatheralarm.data.InternalWeather
import timber.log.Timber

/**
 * WeatherAlarm
 * Class: NotificationUtil
 * Created by pyg10.
 * Created On 2022-02-25.
 * Description:
 */

private const val CHANNEL_ID = "Weather"
private const val channelName = "Notify_Weather"
private const val NOTIFICATION_ID = 14

@RequiresApi(Build.VERSION_CODES.N)
private const val property = NotificationManager.IMPORTANCE_MIN

@SuppressLint("RemoteViewLayout")
fun Context.createNotification(internalWeather: InternalWeather?){

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val remoteExpandView = RemoteViews(packageName, R.layout.layout_expand)

    internalWeather?.let { weather ->
        remoteExpandView.setTextViewText(R.id.tv_expand_location, currentLocation(weather.nx, weather.ny) ?: "알 수 없는 장소")
        weather.setRemoteViewImage(remoteExpandView)
        remoteExpandView.setTextViewText(R.id.tv_temperature, weather.item.find { it.category == "TMP" }?.value?.let { getString(R.string.temperature, it) }?: run { "unKnown" } )
        remoteExpandView.setTextViewText(R.id.tv_max_min_temperature, getString(R.string.max_min_temperature, weather.item.find { it.category == "TMX" }?.value ?: "unKnown", weather.item.find { it.category == "TMN" }?.value ?: "unKnown") )
    }

    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_alarm)
        .setAutoCancel(true)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setContentTitle("테스트")
        .setContentText("테스트입니다.")
        .setCustomBigContentView(remoteExpandView)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        notificationManager.createNotificationChannel(createNotificationChannel(CHANNEL_ID, channelName, property))
    }

    with(NotificationManagerCompat.from(this)){
        notify(NOTIFICATION_ID, builder)
    }
}

private fun InternalWeather.setRemoteViewImage(remoteViews: RemoteViews){
    item.find { it.category == "SKY" }?.let {
        when(it.value){
            "1" -> {
                remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.sunny)
                remoteViews.setTextViewText(R.id.tv_weather_state, "맑음")
            }
            "3" -> {
                remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.cloud)
                remoteViews.setTextViewText(R.id.tv_weather_state, "구름많음")
                Timber.d("3 ${item.find { weatherItem -> weatherItem.category == "PTY" }?.value}")
            }
            "4" -> {
                when(item.find { weatherItem -> weatherItem.category == "PTY" }?.value){
                    "0" -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.cloud)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "흐림")
                    }
                    "1" -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.rain)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "비")
                    }
                    "2" -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.rain)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "비/눈")
                    }
                    "3" -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.rain)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "눈")
                    }
                    "4" -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.rain)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "소나기")
                    }
                    else -> {
                        remoteViews.setImageViewResource(R.id.iv_weather_status, R.drawable.cloud)
                        remoteViews.setTextViewText(R.id.tv_weather_state, "흐림")
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(channelId: String, channelName: String, property: Int): NotificationChannel
    = NotificationChannel(channelId, channelName, property)