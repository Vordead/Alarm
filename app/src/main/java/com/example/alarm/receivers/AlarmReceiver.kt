package com.example.alarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle the alarm event here, e.g., play a sound, show a notification.
        // You can extract alarm data from the intent to identify which alarm triggered.
        if (context != null) {
            // Handle the alarm event here.

            // For in-app functionality (e.g., showing a Toast), you can do something like this:
            Toast.makeText(context, "Alarm is ringing!", Toast.LENGTH_SHORT).show()

            // You can also trigger other actions here, such as showing a notification or playing a sound.
        }
    }

}