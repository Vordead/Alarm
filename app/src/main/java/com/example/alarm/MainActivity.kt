package com.example.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.alarm.data.local.AlarmDatabase
import com.example.alarm.receivers.AlarmReceiver
import com.example.alarm.ui.screens.AlarmRingingScreen
import com.example.alarm.ui.screens.AlarmsScreen
import com.example.alarm.ui.state.AlarmState
import com.example.alarm.ui.theme.AlarmTheme
import com.example.alarm.ui.viewmodels.AlarmViewModel


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext, AlarmDatabase::class.java, "contacts.db"
        ).build()
    }
    private val viewModel by viewModels<AlarmViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AlarmViewModel(db.dao) as T
            }
        }
    })

    @RequiresApi(34)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val timePickerState = rememberTimePickerState()
                    val state by viewModel.state.collectAsState(initial = AlarmState())
                    val alarmMgr = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                    val alarmIntent =
                        Intent(LocalContext.current, AlarmReceiver::class.java).let { intent ->
                            PendingIntent.getBroadcast(
                                LocalContext.current, 0, intent, PendingIntent.FLAG_IMMUTABLE
                            )
                        }
                    NavHost(navController = navController, startDestination = "home") {
                        composable("about") {

                        }
                        composable("alarm_ringing") {
                            if (alarmMgr != null) {
                                AlarmRingingScreen(
                                    alarmManager = alarmMgr, pendingIntent = alarmIntent
                                )
                            }
                        }
                        navigation(startDestination = "alarms_screen", route = "home") {
                            composable("alarms_screen") {

                                if (alarmMgr != null) {
                                    AlarmsScreen(
                                        state = state,
                                        timePickerState = timePickerState,
                                        onEvent = viewModel::onEvent,
                                        alarmManager = alarmMgr,
                                        pendingIntent = alarmIntent
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> NavBackStackEntry.sharedViewModel(
    navController: NavController
) : T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}