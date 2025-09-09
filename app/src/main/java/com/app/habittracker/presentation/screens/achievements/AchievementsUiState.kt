package com.app.habittracker.presentation.screens.achievements

import com.app.habittracker.domain.model.Achievement
import com.app.habittracker.domain.model.AchievementLevel

data class AchievementsUiState(
    val allAchievements: List<AchievementWithHabit> = emptyList(),
    val unlockedCount: Int = 0,
    val totalCount: Int = AchievementLevel.values().size,
    val isLoading: Boolean = false
)

data class AchievementWithHabit(
    val achievement: Achievement?,
    val level: AchievementLevel,
    val habitName: String,
    val isUnlocked: Boolean
)