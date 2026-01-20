package com.app.habittracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.app.habittracker.data.local.datastore.PreferencesManager
import com.app.habittracker.presentation.screens.main.MainScreen
import com.app.habittracker.presentation.theme.HabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deepLinkAction = parseDeepLink(intent)

        setContent {
            val themeMode by preferencesManager.themeMode.collectAsState(
                initial = com.app.habittracker.domain.model.ThemeMode.SYSTEM
            )

            HabitTrackerTheme(themeMode = themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(initialAction = deepLinkAction)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun parseDeepLink(intent: Intent?): String? {
        val data = intent?.data ?: return null
        return when (data.host) {
            "add_habit" -> "add_habit"
            "achievements" -> "achievements"
            else -> null
        }
    }
}