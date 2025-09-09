package com.app.habittracker.data.local.database.dao

import androidx.room.*
import com.app.habittracker.data.local.database.entity.AchievementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements ORDER BY unlockedAt DESC")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Query("SELECT * FROM achievements WHERE habitId = :habitId")
    suspend fun getAchievementsForHabit(habitId: Long): List<AchievementEntity>

    @Query("SELECT * FROM achievements WHERE habitId = :habitId AND level = :level")
    suspend fun getAchievement(habitId: Long, level: String): AchievementEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAchievement(achievement: AchievementEntity): Long

    @Update
    suspend fun updateAchievement(achievement: AchievementEntity)

    @Query("UPDATE achievements SET isNotified = 1 WHERE id = :id")
    suspend fun markAsNotified(id: Long)

    @Query("DELETE FROM achievements WHERE habitId = :habitId")
    suspend fun deleteAchievementsForHabit(habitId: Long)
}