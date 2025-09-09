package com.app.habittracker.presentation.screens.settings

import com.app.habittracker.domain.model.ThemeMode

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val notificationsEnabled: Boolean = true,
    val dailyQuotesEnabled: Boolean = true,
    val appVersion: String = "1.0.0"
)