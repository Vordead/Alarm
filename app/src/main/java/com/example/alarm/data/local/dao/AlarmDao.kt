package com.example.alarm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.alarm.data.local.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Upsert
    suspend fun upsertAlarm(alarmEntity: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Query("SELECT * FROM alarmentity")
    fun getAlarms() : Flow<List<AlarmEntity>>

    @Query("SELECT MAX(id) FROM alarmentity")
    suspend fun getMaxAlarmId(): Int?}