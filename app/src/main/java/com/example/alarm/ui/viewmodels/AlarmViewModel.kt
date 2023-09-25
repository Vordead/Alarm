package com.example.alarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.data.local.dao.AlarmDao
import com.example.alarm.data.local.entities.AlarmEntity
import com.example.alarm.ui.events.AlarmEvent
import com.example.alarm.ui.state.AlarmState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val dao: AlarmDao
) : ViewModel() {
    private val triggerUpdate = MutableStateFlow(Unit)
    private val _alarms = combine(triggerUpdate, dao.getAlarms()) { _, alarmEntities ->
        alarmEntities.map { it.toAlarm() }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    private val _state = MutableStateFlow(AlarmState())
    val state = combine(_state, _alarms) { state, alarms ->
        state.copy(
            alarms = alarms.map { it.toAlarmEntity() },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AlarmState())


    fun onEvent(event: AlarmEvent) {
        when (event) {

            is AlarmEvent.HideDialog -> {
                _state.update { it.copy(isAddingAlarm = false) }
            }

            is AlarmEvent.ShowDialog -> {
                _state.update { it.copy(isAddingAlarm = true) }
            }

            is AlarmEvent.DeleteAlarm -> {
                viewModelScope.launch {
                    dao.deleteAlarm(event.alarm.toAlarmEntity())
                }
            }

            is AlarmEvent.SaveAlarm -> {
                viewModelScope.launch {
                    if(event.alarm != null){
                        _state.update {
                            it.copy(isActive = _state.value.isActive)
                        }
                        val alarmEntity = AlarmEntity(
                            id=event.alarm.id,
                            alarmHour = _state.value.hour,
                            alarmMinute = _state.value.minute,
                            is24H = _state.value.is24H,
                            isActive = _state.value.isActive,
                            label = "School Alarm"
                        )
                        dao.upsertAlarm(alarmEntity)
                    }
                    else{
                        val alarmEntity = AlarmEntity(
                            alarmHour = _state.value.hour,
                            alarmMinute = _state.value.minute,
                            is24H = _state.value.is24H,
                            isActive = _state.value.isActive,
                            label = "School Alarm"
                        )
                        dao.upsertAlarm(alarmEntity)
                    }
                }

                _state.update {
                    it.copy(
                        isAddingAlarm = false
                    )
                }
            }

            is AlarmEvent.SetAlarmIsActive->{
                _state.update {
                    it.copy(
                        isActive = event.isActive
                    )
                }
            }



            is AlarmEvent.SetAlarmDate -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }

            is AlarmEvent.SetAlarmHour -> {
                _state.update {
                    it.copy(
                        hour = event.hour
                    )
                }
            }

            is AlarmEvent.SetAlarmMinute -> {
                _state.update {
                    it.copy(
                        minute = event.minute
                    )
                }
            }

            is AlarmEvent.SetAlarmIs24H -> {
                _state.update {
                    it.copy(
                        is24H = event.is24H
                    )
                }
            }
        }
    }
}