package com.darekbx.notebookcheckreader.repository

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.darekbx.notebookcheckreader.R

class RssNotificationManager(
    private val context: Context,
    private val notificationManager: NotificationManager
) {

    fun showNotification(title: String, message: String) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Run refresh in background",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_rss_feed)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val CHANNEL_ID = "work_results_channel"
        const val NOTIFICATION_ID = 51237
    }
}