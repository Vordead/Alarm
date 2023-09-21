package com.example.alarm.ui.events

import com.example.alarm.data.models.Alarm

sealed interface AlarmEvent {
    object SaveAlarm : AlarmEvent
    data class DeleteAlarm(val alarm: Alarm) : AlarmEvent
    object ShowDialog : AlarmEvent
    object HideDialog : AlarmEvent
    data class SetAlarmDate(val date: String) : AlarmEvent
    data class SetAlarmHour(val hour: Int) : AlarmEvent
    data class SetAlarmMinute(val minute: Int) : AlarmEvent
    data class SetAlarmIs24H(val is24H: Boolean) : AlarmEvent
}