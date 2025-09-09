package com.app.habittracker.domain.repository

import com.app.habittracker.domain.model.Achievement
import com.app.habittracker.domain.model.AchievementLevel
import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAllAchievements(): Flow<List<Achievement>>
    suspend fun getAchievementsForHabit(habitId: Long): List<Achievement>
    suspend fun unlockAchievement(habitId: Long, level: AchievementLevel): Boolean
    suspend fun markAsNotified(achievementId: Long)
}