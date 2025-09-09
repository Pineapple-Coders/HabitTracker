package com.app.habittracker.presentation.screens.home

import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.Quote

data class HomeUiState(
    val habits: List<Habit> = emptyList(),
    val dailyQuote: Quote? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)