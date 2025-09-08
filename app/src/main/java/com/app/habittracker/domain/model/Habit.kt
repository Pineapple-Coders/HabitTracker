package com.app.habittracker.domain.model

import kotlinx.datetime.Instant

data class Habit(
    val id: Long = 0,
    val name: String,
    val icon: HabitIcon,
    val startDate: Instant,
    val lastResetDate: Instant? = null,
    val longestStreak: Long = 0, // in seconds
    val resetHistory: List<ResetHistory> = emptyList(),
    val createdAt: Instant = Instant.fromEpochMilliseconds(System.currentTimeMillis()),
    val updatedAt: Instant = Instant.fromEpochMilliseconds(System.currentTimeMillis())
)