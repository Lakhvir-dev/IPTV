package com.dhiman.iptv.util

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dhiman.iptv.R

internal object NotificationHelper {

    private var notificationManager: NotificationManager? = null
    @SuppressLint("StaticFieldLeak")
    private var notificationBuilder: NotificationCompat.Builder? = null

    private var singleNotificationId = 100

    fun createNotification(context: Context) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //replace with your own image
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(ConstantUtil.DOWNLOAD_CHANNEL_ID, "Recording", NotificationManager.IMPORTANCE_LOW)
            notificationChannel.description = "Live Recording"
            notificationChannel.setShowBadge(false)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
        val `when` = System.currentTimeMillis()

        // Create a new Notification
        notificationBuilder = NotificationCompat.Builder(context, ConstantUtil.DOWNLOAD_CHANNEL_ID)
            .setShowWhen(true)
            .setWhen(`when`)
            // Set the Notification color
            .setColor(ContextCompat.getColor(context, R.color.flush_mahogany))
            // Set small icons
            .setSmallIcon(R.drawable.logo)
            // Set Notification content information
            .setContentText("Recording")
            .setContentTitle("Live Recording")
            .setProgress(0, 0, false)
            .setOngoing(true)

        notificationManager!!.notify(singleNotificationId, notificationBuilder!!.build())
    }

    fun downloadComplete() {
        notificationBuilder!!.setContentText("Recording Completed")
        notificationBuilder!!.setOngoing(false)
        notificationManager!!.notify(singleNotificationId, notificationBuilder!!.build())
    }

    fun updateProgress(minutes: String, second: String, maxSize: String) {
        val time = "$minutes:$second - $maxSize:00"
        notificationBuilder!!.setContentText(time)
        notificationManager!!.notify(singleNotificationId, notificationBuilder!!.build())
    }

}