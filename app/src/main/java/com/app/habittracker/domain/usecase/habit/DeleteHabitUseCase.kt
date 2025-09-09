package com.app.habittracker.domain.usecase.habit

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val analytics: FirebaseAnalytics
) {
    suspend operator fun invoke(habit: Habit) {
        habitRepository.deleteHabit(habit)

        // Log to Firebase Analytics
        analytics.logEvent(Constants.EVENT_HABIT_DELETED, null)
    }
}