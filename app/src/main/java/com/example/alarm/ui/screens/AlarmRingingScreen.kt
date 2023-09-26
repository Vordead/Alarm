package com.example.alarm.ui.screens

import android.app.AlarmManager
import android.app.PendingIntent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarm.ui.components.Clock
import kotlinx.coroutines.delay

@RequiresApi(34)
@Composable
fun AlarmRingingScreen(
    alarmManager: AlarmManager,
    pendingIntent: PendingIntent
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val milliseconds = remember {
            System.currentTimeMillis()
        }
        var seconds by remember {
            mutableStateOf((milliseconds / 1000f) % 60f)
        }
        var minutes by remember {
            mutableStateOf(((milliseconds / 1000f) / 60) % 60f)
        }
        var hours by remember {
            mutableStateOf((milliseconds / 1000f) / 3600f + 2f)
        }
        LaunchedEffect(key1 = seconds) {
            delay(1000L)
            minutes += 1f / 60f
            hours += 1f / (60f * 12f)
            seconds += 1f
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Clock(
                seconds = seconds, minutes = minutes, hours = hours
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                alarmManager.cancel(pendingIntent)
            }) {
                Text(text = "Stop Alarm")
            }
        }

    }
}


