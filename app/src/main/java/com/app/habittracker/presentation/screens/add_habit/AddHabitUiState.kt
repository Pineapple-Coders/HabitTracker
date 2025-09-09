package com.app.habittracker.presentation.screens.add_habit

import com.app.habittracker.domain.model.HabitIcon

data class AddHabitUiState(
    val habitName: String = "",
    val selectedIcon: HabitIcon = HabitIcon.SMOKING,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)