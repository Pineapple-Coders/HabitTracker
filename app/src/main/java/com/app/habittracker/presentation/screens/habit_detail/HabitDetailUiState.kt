package com.app.habittracker.presentation.screens.habit_detail

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.ResetHistory

data class HabitDetailUiState(
    val habit: Habit? = null,
    val currentStreakDays: Int = 0,
    val currentStreakHours: Int = 0,
    val currentStreakMinutes: Int = 0,
    val longestStreakDays: Int = 0,
    val longestStreakHours: Int = 0,
    val resetHistory: List<ResetHistory> = emptyList(),
    val showResetDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showEncouragementMessage: String? = null,
    val isLoading: Boolean = false
)