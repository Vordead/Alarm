package com.example.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.alarm.data.local.AlarmDatabase
import com.example.alarm.ui.screens.AlarmsScreen
import com.example.alarm.ui.state.AlarmState
import com.example.alarm.ui.theme.AlarmTheme
import com.example.alarm.ui.viewmodels.AlarmViewModel


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            "contacts.db"
        ).build()
    }
    private val viewModel by viewModels<AlarmViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlarmViewModel(db.dao) as T
                }
            }
        }
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var showTimePicker by remember { mutableStateOf(false) }
                    val timePickerState = rememberTimePickerState()
                    var isDialogOpen by remember { mutableStateOf(false) }
                    val state by viewModel.state.collectAsState(initial = AlarmState())
                    AlarmsScreen(state = state, timePickerState = timePickerState ,onEvent = viewModel::onEvent)




//                        Box(modifier = Modifier.fillMaxSize()) {
//                            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
//                                IconButton(modifier = Modifier
//                                    .align(Alignment.Center)
//                                    .size(100.dp)
//                                    .padding(16.dp)
//                                    .background(
//                                        color = MaterialTheme.colorScheme.primaryContainer,
//                                        shape = RoundedCornerShape(16.dp)
//                                    ), onClick = {
//
//                                    showTimePicker = true
//                                    isDialogOpen = true
//                                }) {
//                                    Icon(
//                                        Icons.Filled.Add,
//                                        "contentDescription",
//                                    )
//                                }
//
//                            }
//                            if (isDialogOpen) {
//                                CustomDialog(timePickerState) {
//                                    isDialogOpen = false
//                                }
//                            }
//                        }
//                        Box(
//                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
//                        ) {
//                            val milliseconds = remember {
//                                System.currentTimeMillis()
//                            }
//                            var seconds by remember {
//                                mutableStateOf((milliseconds / 1000f) % 60f)
//                            }
//                            var minutes by remember {
//                                mutableStateOf(((milliseconds / 1000f) / 60) % 60f)
//                            }
//                            var hours by remember {
//                                mutableStateOf((milliseconds / 1000f) / 3600f + 2f)
//                            }
//                            LaunchedEffect(key1 = seconds) {
//                                delay(1000L)
//                                minutes += 1f / 60f
//                                hours += 1f / (60f * 12f)
//                                seconds += 1f
//                            }
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Clock(
//                                    seconds = seconds, minutes = minutes, hours = hours
//                                )
//                                Spacer(modifier = Modifier.height(32.dp))
//                                Text(
//                                    text = "Alarm is Inactive",
//                                    style = MaterialTheme.typography.headlineLarge,
//                                )
//                            }
//
//                        }
                    }
                }

        }
    }
}