package com.example.alarm.ui.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarm.R
import com.example.alarm.ui.components.AddAlarmDialog
import com.example.alarm.ui.events.AlarmEvent
import com.example.alarm.ui.state.AlarmState
import kotlinx.coroutines.launch
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmsScreen(
    state: AlarmState,
    timePickerState: TimePickerState,
    onEvent: (AlarmEvent) -> Unit,
    alarmManager: AlarmManager,
    pendingIntent: PendingIntent
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(AlarmEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = "Add contact"
                )
            }
        },
    ) { _ ->
        if (state.isAddingAlarm) {
            AddAlarmDialog(state = state, timePickerState = timePickerState, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.alarms, key = { alarm -> alarm.id }) { alarm ->
                val isActiveState = remember { mutableStateOf(alarm.isActive) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${alarm.alarmHour}:${alarm.alarmMinute}",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "School Alarm", style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row {
                        IconButton(onClick = {
                            isActiveState.value = !isActiveState.value
                            onEvent(AlarmEvent.SetAlarmIsActive(isActiveState.value))
                            onEvent(AlarmEvent.SaveAlarm(alarm.toAlarm()))
                            if (isActiveState.value) {
                                val calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                    set(Calendar.HOUR_OF_DAY, alarm.alarmHour)
                                    set(Calendar.MINUTE, alarm.alarmMinute)
                                }

                                alarmManager.canScheduleExactAlarms()
                                alarmManager.setExactAndAllowWhileIdle(
                                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                                )
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Alarm set for ${alarm.alarmHour}:${alarm.alarmMinute}"
                                    )
                                }
                            }
                        }) {
                            Icon(
                                painterResource(
                                    id = if (isActiveState.value) R.drawable.baseline_alarm_on_24 else {
                                        R.drawable.baseline_alarm_off_24
                                    }
                                ), contentDescription = null
                            )
                        }
                        IconButton(onClick = {
                            onEvent(AlarmEvent.DeleteAlarm(alarm.toAlarm()))
                            Log.d("mh", state.alarms.toString())
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete alarm"
                            )
                        }
                    }
                }
            }
        }
    }
}