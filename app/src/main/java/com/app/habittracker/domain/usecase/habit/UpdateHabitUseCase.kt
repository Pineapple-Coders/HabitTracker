package com.app.habittracker.domain.usecase.habit

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.repository.HabitRepository
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit) {
        habitRepository.updateHabit(habit)
    }
}