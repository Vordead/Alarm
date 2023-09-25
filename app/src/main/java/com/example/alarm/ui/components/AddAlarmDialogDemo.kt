package com.example.alarm.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarm.ui.events.AlarmEvent
import com.example.alarm.ui.state.AlarmState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmDialogDemo(
    state: AlarmState,
    timePickerState: TimePickerState,
    onEvent: (AlarmEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(AlarmEvent.ShowDialog) },
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Add Alarm")
                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    state.date,
                    onValueChange = {
                        onEvent(AlarmEvent.SetAlarmDate(it))
                    },
                    placeholder = {
                        Text(text = "12:35")
                    }
                )
                TextButton(
                    onClick = { onEvent(AlarmEvent.HideDialog) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Cancel")
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        onEvent(AlarmEvent.AddAlarm)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}