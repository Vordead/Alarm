package com.example.alarm.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarm.data.models.Alarm

@Entity
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val timeInMillis: Long,
    val label: String,
    val isActive: Boolean) {
    fun toAlarm(): Alarm {
        return Alarm(id,timeInMillis, label, isActive)
    }
}