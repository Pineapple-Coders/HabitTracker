package com.app.habittracker.domain.usecase.timer

import javax.inject.Inject
import kotlin.time.Duration

class FormatTimeUseCase @Inject constructor() {

    operator fun invoke(duration: Duration): String {
        val days = duration.inWholeDays
        val hours = duration.inWholeHours % 24
        val minutes = duration.inWholeMinutes % 60

        return when {
            days > 0 -> "$days days ${hours} hours"
            hours > 0 -> "$hours hours ${minutes} mins"
            else -> "$minutes mins"
        }
    }
}