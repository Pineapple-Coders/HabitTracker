package com.app.habittracker.domain.model

import kotlinx.datetime.Instant

data class ResetHistory(
    val id: Long = 0,
    val habitId: Long,
    val resetDate: Instant,
    val streakDuration: Long // in seconds
)