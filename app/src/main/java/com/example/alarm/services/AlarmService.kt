package com.example.alarm.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Handle alarm-related tasks here.
        // This is where you might play a sound or show a notification.

        // You should also consider starting the service in the foreground
        // for long-running tasks to prevent it from being killed by the system.

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}