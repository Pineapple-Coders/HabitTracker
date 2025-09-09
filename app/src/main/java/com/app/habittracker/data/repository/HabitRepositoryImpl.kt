package com.app.habittracker.data.repository

import com.app.habittracker.data.local.database.dao.HabitDao
import com.app.habittracker.data.local.database.entity.ResetHistoryEntity
import com.app.habittracker.data.mapper.toDomain
import com.app.habittracker.data.mapper.toEntity
import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.ResetHistory
import com.app.habittracker.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao
) : HabitRepository {

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map { entity ->
                val resetHistory = habitDao.getResetHistoryForHabit(entity.id)
                    .map { it.toDomain() }
                entity.toDomain(resetHistory)
            }
        }
    }

    override suspend fun getHabitById(id: Long): Habit? {
        return habitDao.getHabitById(id)?.let { entity ->
            val resetHistory = habitDao.getResetHistoryForHabit(id)
                .map { it.toDomain() }
            entity.toDomain(resetHistory)
        }
    }

    override suspend fun insertHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(
            habit.copy(
                updatedAt = Clock.System.now()
            ).toEntity()
        )
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
    }

    override suspend fun getHabitCount(): Int {
        return habitDao.getHabitCount()
    }

    override suspend fun resetHabit(habitId: Long) {
        val habit = habitDao.getHabitById(habitId) ?: return
        val now = Clock.System.now()

        // Calculate streak duration
        val streakStart = habit.lastResetDate ?: habit.startDate
        val streakDuration = (now - streakStart).inWholeSeconds

        // Save reset history
        habitDao.insertResetHistory(
            ResetHistoryEntity(
                habitId = habitId,
                resetDate = now,
                streakDuration = streakDuration
            )
        )

        // Update habit with new reset date and longest streak if needed
        val updatedHabit = habit.copy(
            lastResetDate = now,
            longestStreak = maxOf(habit.longestStreak, streakDuration),
            updatedAt = now
        )
        habitDao.updateHabit(updatedHabit)
    }

    override suspend fun getResetHistoryForHabit(habitId: Long): List<ResetHistory> {
        return habitDao.getResetHistoryForHabit(habitId).map { it.toDomain() }
    }
}