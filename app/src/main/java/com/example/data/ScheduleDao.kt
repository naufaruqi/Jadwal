package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ScheduleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules ORDER BY dayOfWeek ASC, startTime ASC")
    fun getAllSchedules(): Flow<List<ScheduleItem>>

    @Query("SELECT * FROM schedules WHERE dayOfWeek = :day ORDER BY startTime ASC")
    fun getSchedulesForDay(day: Int): Flow<List<ScheduleItem>>

    @Query("SELECT * FROM schedules WHERE id = :id LIMIT 1")
    suspend fun getScheduleById(id: Long): ScheduleItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: ScheduleItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<ScheduleItem>)

    @Update
    suspend fun updateSchedule(schedule: ScheduleItem)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleItem)

    @Query("DELETE FROM schedules")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM schedules")
    suspend fun getCount(): Int
}
