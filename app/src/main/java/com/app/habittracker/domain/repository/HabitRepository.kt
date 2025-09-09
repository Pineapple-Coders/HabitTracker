package com.app.habittracker.domain.repository

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.ResetHistory
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    suspend fun getHabitById(id: Long): Habit?
    suspend fun insertHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    suspend fun getHabitCount(): Int
    suspend fun resetHabit(habitId: Long)
    suspend fun getResetHistoryForHabit(habitId: Long): List<ResetHistory>
}