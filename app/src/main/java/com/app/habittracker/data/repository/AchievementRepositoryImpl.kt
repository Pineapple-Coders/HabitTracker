package com.app.habittracker.data.repository

import com.app.habittracker.data.local.database.dao.AchievementDao
import com.app.habittracker.data.mapper.toDomain
import com.app.habittracker.data.mapper.toEntity
import com.app.habittracker.domain.model.Achievement
import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementRepositoryImpl @Inject constructor(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override fun getAllAchievements(): Flow<List<Achievement>> {
        return achievementDao.getAllAchievements().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getAchievementsForHabit(habitId: Long): List<Achievement> {
        return achievementDao.getAchievementsForHabit(habitId).map { it.toDomain() }
    }

    override suspend fun unlockAchievement(habitId: Long, level: AchievementLevel): Boolean {
        val existing = achievementDao.getAchievement(habitId, level.name)
        if (existing != null) {
            return false // Already unlocked
        }

        val achievement = Achievement(
            habitId = habitId,
            level = level,
            unlockedAt = Clock.System.now(),
            isNotified = false
        )

        achievementDao.insertAchievement(achievement.toEntity())
        return true
    }

    override suspend fun markAsNotified(achievementId: Long) {
        achievementDao.markAsNotified(achievementId)
    }
}