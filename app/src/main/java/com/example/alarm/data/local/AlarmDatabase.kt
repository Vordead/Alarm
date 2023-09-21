package com.example.alarm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarm.data.local.dao.AlarmDao
import com.example.alarm.data.local.entities.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val dao : AlarmDao
}