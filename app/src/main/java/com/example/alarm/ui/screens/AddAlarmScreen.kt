package com.example.alarm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alarm.ui.events.AlarmEvent
import com.example.alarm.ui.state.AlarmState

@Composable
fun AddAlarmScreen(
    state: AlarmState,
    onEvent:(AlarmEvent)->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.SpaceAround
    ) {

    }
}