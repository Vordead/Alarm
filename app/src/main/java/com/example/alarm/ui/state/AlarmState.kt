package com.example.alarm.ui.state

import com.example.alarm.data.local.entities.AlarmEntity
import com.example.alarm.data.models.Alarm

data class AlarmState(
    val alarms: List<AlarmEntity> = emptyList(),
    val date: String = "",
    val isAddingAlarm: Boolean = false
){
    fun toAlarms() : List<Alarm>{
        return alarms.map {
            it.toAlarm()
        }
    }
}