package com.example.alarm.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.data.local.dao.AlarmDao
import com.example.alarm.data.models.Alarm
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
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _state = MutableStateFlow(AlarmState())
    val state = combine(_state,_alarms) {state, alarms ->
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

            is AlarmEvent.SaveAlarm->{

                viewModelScope.launch {
                    val currentMaxId = dao.getMaxAlarmId() ?: 0
                    val newAlarmId = currentMaxId + 1
                    val alarm = Alarm(
                        id = newAlarmId,
                        timeInMillis = _state.value.date.toLong(),
                        label = "School Alarm",
                        isActive = true
                    )
                    dao.upsertAlarm(alarm.toAlarmEntity())

                }

                _state.update { it.copy(
                    isAddingAlarm = false
                ) }
            }
            is AlarmEvent.SetAlarmDate->{
                _state.update { it.copy(
                    date = event.date
                ) }
            }
        }
    }
}