package com.app.habittracker.data.local.database.dao


import androidx.room.*
import com.app.habittracker.data.local.database.entity.HabitEntity
import com.app.habittracker.data.local.database.entity.ResetHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: Long): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT COUNT(*) FROM habits")
    suspend fun getHabitCount(): Int

    @Query("SELECT * FROM reset_history WHERE habitId = :habitId ORDER BY resetDate DESC")
    suspend fun getResetHistoryForHabit(habitId: Long): List<ResetHistoryEntity>

    @Insert
    suspend fun insertResetHistory(resetHistory: ResetHistoryEntity)

    @Query("DELETE FROM reset_history WHERE habitId = :habitId")
    suspend fun deleteResetHistoryForHabit(habitId: Long)
}