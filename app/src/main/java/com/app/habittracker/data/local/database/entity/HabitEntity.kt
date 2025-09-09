package com.app.habittracker.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val iconName: String,
    val startDate: Instant,
    val lastResetDate: Instant? = null,
    val longestStreak: Long = 0, // in seconds
    val createdAt: Instant,
    val updatedAt: Instant
)