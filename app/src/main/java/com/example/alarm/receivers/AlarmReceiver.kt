package com.example.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle the alarm event here, e.g., play a sound, show a notification.
        // You can extract alarm data from the intent to identify which alarm triggered.
    }
}