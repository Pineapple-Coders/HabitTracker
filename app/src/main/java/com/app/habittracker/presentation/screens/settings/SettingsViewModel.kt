package com.app.habittracker.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.data.local.datastore.PreferencesManager
import com.app.habittracker.domain.model.ThemeMode
import com.app.habittracker.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val analytics: FirebaseAnalytics
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        preferencesManager.themeMode,
        preferencesManager.notificationsEnabled,
        preferencesManager.dailyQuotesEnabled
    ) { themeMode, notificationsEnabled, dailyQuotesEnabled ->
        SettingsUiState(
            themeMode = themeMode,
            notificationsEnabled = notificationsEnabled,
            dailyQuotesEnabled = dailyQuotesEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            preferencesManager.setThemeMode(themeMode)
            analytics.logEvent(Constants.EVENT_APP_THEME_CHANGED, null)
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationsEnabled(enabled)
        }
    }

    fun updateDailyQuotesEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setDailyQuotesEnabled(enabled)
        }
    }
}