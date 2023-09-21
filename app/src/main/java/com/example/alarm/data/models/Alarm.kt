package com.example.alarm.data.models

import android.os.Parcelable
import com.example.alarm.data.local.entities.AlarmEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alarm(
    val id: Int,
    val timeInMillis: Long,
    val label: String,
    val isActive: Boolean,
) : Parcelable {
    fun toAlarmEntity(): AlarmEntity {
        return AlarmEntity(
            id=id,
            timeInMillis =  timeInMillis,
            label=  label,
            isActive = isActive)
    }
}