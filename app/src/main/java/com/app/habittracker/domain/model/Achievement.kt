package com.app.habittracker.domain.model

import kotlinx.datetime.Instant

data class Achievement(
    val id: Long = 0,
    val habitId: Long,
    val level: AchievementLevel,
    val unlockedAt: Instant,
    val isNotified: Boolean = false
)