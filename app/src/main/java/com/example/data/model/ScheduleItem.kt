package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedules")
data class ScheduleItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subject: String,
    val teacher: String = "",
    val room: String = "",
    val dayOfWeek: Int, // 1 = Senin, 2 = Selasa, 3 = Rabu, 4 = Kamis, 5 = Jumat, 6 = Sabtu, 7 = Minggu
    val startTime: String, // HH:mm format, e.g. "07:30"
    val endTime: String,   // HH:mm format, e.g. "09:00"
    val colorHex: String = "#3B82F6",
    val notes: String = ""
) {
    val dayName: String
        get() = when (dayOfWeek) {
            1 -> "Senin"
            2 -> "Selasa"
            3 -> "Rabu"
            4 -> "Kamis"
            5 -> "Jumat"
            6 -> "Sabtu"
            7 -> "Minggu"
            else -> "Lainnya"
        }

    val durationMinutes: Int
        get() {
            return try {
                val startParts = startTime.split(":").map { it.toInt() }
                val endParts = endTime.split(":").map { it.toInt() }
                val startTotal = startParts[0] * 60 + startParts[1]
                val endTotal = endParts[0] * 60 + endParts[1]
                if (endTotal >= startTotal) endTotal - startTotal else 0
            } catch (e: Exception) {
                0
            }
        }
}
