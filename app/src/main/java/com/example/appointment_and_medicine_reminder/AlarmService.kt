package com.example.appointment_and_medicine_reminder

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings

class AlarmService : Service() {

    var mediaPlayer: MediaPlayer? = null

    companion object {
        const val CHANNEL_ID = "AlarmServiceChannel"
        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Reminder")
                .setContentText("Your scheduled reminder is active")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle("Reminder")
                .setContentText("Your scheduled reminder is active")
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .build()
        }

        startForeground(NOTIFICATION_ID, notification)

        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}