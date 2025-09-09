package com.app.habittracker.domain.usecase.timer

import com.app.habittracker.domain.model.Habit
import kotlinx.datetime.Clock
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class CalculateStreakUseCase @Inject constructor() {

    data class StreakInfo(
        val currentStreak: Duration,
        val longestStreak: Duration,
        val daysClean: Int,
        val hoursClean: Int,
        val minutesClean: Int
    )

    operator fun invoke(habit: Habit): StreakInfo {
        val now = Clock.System.now()
        val streakStart = habit.lastResetDate ?: habit.startDate
        val currentStreakDuration = now - streakStart
        val longestStreakDuration = maxOf(
            habit.longestStreak.seconds,
            currentStreakDuration
        )

        val totalSeconds = currentStreakDuration.inWholeSeconds
        val days = (totalSeconds / 86400).toInt()
        val hours = ((totalSeconds % 86400) / 3600).toInt()
        val minutes = ((totalSeconds % 3600) / 60).toInt()

        return StreakInfo(
            currentStreak = currentStreakDuration,
            longestStreak = longestStreakDuration,
            daysClean = days,
            hoursClean = hours,
            minutesClean = minutes
        )
    }
}