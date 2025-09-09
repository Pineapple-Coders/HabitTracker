package com.app.habittracker.domain.usecase.habit

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(): Flow<List<Habit>> {
        return habitRepository.getAllHabits()
    }
}