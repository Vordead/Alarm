package com.example.alarm.data.models

import android.os.Parcelable
import com.example.alarm.data.local.entities.AlarmEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alarm(
    val id: Int,
    val alarmHour: Int,
    val alarmMinute: Int,
    val is24H: Boolean,
    val label: String,
    val isActive: Boolean,
) : Parcelable {
    fun toAlarmEntity(): AlarmEntity {
        return AlarmEntity(
            id = id,
            alarmHour = alarmHour,
            alarmMinute = alarmMinute,
            is24H = is24H,
            label = label,
            isActive = isActive
        )
    }
}