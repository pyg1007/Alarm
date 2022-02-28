package kr.ryan.weatheralarm.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kr.ryan.weatheralarm.R
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

@RequiresApi(Build.VERSION_CODES.N)
private const val property = NotificationManager.IMPORTANCE_MIN

@SuppressLint("RemoteViewLayout")
fun Context.createNotification(){

    Timber.d("createNotify")

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val remoteSummaryView = RemoteViews(packageName, R.layout.layout_summary)
    val remoteExpandView = RemoteViews(packageName, R.layout.layout_expand)

    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_alarm)
        .setAutoCancel(true)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(remoteSummaryView)
        .setCustomBigContentView(remoteExpandView)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        notificationManager.createNotificationChannel(createNotificationChannel(CHANNEL_ID, channelName, property))
    }

    with(NotificationManagerCompat.from(this)){
        Timber.d("notification notify")
        notify(14, builder)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(channelId: String, channelName: String, property: Int): NotificationChannel
    = NotificationChannel(channelId, channelName, property)