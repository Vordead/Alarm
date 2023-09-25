package com.example.alarm.ui.state

import com.example.alarm.data.local.entities.AlarmEntity
import com.example.alarm.data.models.Alarm

data class AlarmState(
    val alarms: List<AlarmEntity> = emptyList(),
    val date: String = "",
    val hour : Int = 0,
    val minute: Int =0,
    val is24H: Boolean = false,
    val isAddingAlarm: Boolean = false,
    val isActive: Boolean = true
){
    fun toAlarms() : List<Alarm>{
        return alarms.map {
            it.toAlarm()
        }
    }
}