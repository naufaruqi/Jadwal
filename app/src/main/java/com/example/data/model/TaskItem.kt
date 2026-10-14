package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subject: String = "",
    val dueDate: String = "", // YYYY-MM-DD or friendly string like "Besok", "18 Okt"
    val isCompleted: Boolean = false,
    val priority: String = "Sedang", // "Tinggi", "Sedang", "Rendah"
    val notes: String = ""
)
