package com.example.alarm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarm.data.models.Alarm

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val alarmHour: Int,
    val alarmMinute: Int,
    val is24H: Boolean,
    val label: String,
    val isActive: Boolean) {
    fun toAlarm(): Alarm {
        return Alarm(
            id, alarmHour, alarmMinute, is24H, label, isActive
        )
    }
}