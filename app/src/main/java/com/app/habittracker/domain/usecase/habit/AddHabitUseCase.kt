package com.app.habittracker.domain.usecase.habit

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.datetime.Clock
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val analytics: FirebaseAnalytics
) {
    sealed class Result {
        data class Success(val habitId: Long) : Result()
        object MaxHabitsReached : Result()
        object InvalidName : Result()
    }

    suspend operator fun invoke(habit: Habit): Result {
        // Validate habit name
        if (habit.name.isBlank()) {
            return Result.InvalidName
        }

        // Check if max habits reached
        val currentCount = habitRepository.getHabitCount()
        if (currentCount >= Constants.MAX_HABITS) {
            return Result.MaxHabitsReached
        }

        // Add habit with current timestamp
        val habitToAdd = habit.copy(
            startDate = Clock.System.now(),
            createdAt = Clock.System.now(),
            updatedAt = Clock.System.now()
        )

        val habitId = habitRepository.insertHabit(habitToAdd)

        // Log to Firebase Analytics
        analytics.logEvent(Constants.EVENT_HABIT_CREATED, null)

        return Result.Success(habitId)
    }
}