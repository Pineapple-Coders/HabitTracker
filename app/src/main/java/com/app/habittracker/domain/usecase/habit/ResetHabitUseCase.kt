package com.app.habittracker.domain.usecase.habit

import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class ResetHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val analytics: FirebaseAnalytics
) {
    suspend operator fun invoke(habitId: Long) {
        habitRepository.resetHabit(habitId)

        // Log to Firebase Analytics
        analytics.logEvent(Constants.EVENT_HABIT_RESET, null)
    }
}