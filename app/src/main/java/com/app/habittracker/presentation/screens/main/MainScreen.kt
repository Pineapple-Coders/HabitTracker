package com.app.habittracker.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.habittracker.presentation.screens.achievements.AchievementsScreen
import com.app.habittracker.presentation.screens.add_habit.AddHabitScreen
import com.app.habittracker.presentation.screens.habit_detail.HabitDetailScreen
import com.app.habittracker.presentation.screens.home.HomeScreen
import com.app.habittracker.presentation.screens.settings.SettingsScreen
import com.app.habittracker.presentation.theme.LocalAppColors
import com.app.habittracker.presentation.theme.TealAccent

@Composable
fun MainScreen(initialAction: String? = null) {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }
    val appColors = LocalAppColors.current

    // Handle initial action from deep link
    LaunchedEffect(initialAction) {
        when (initialAction) {
            "add_habit" -> navController.navigate("add_habit")
            "achievements" -> selectedTab = 1
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = appColors.cardBackground,
                contentColor = appColors.textPrimary
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = if (selectedTab == 0) TealAccent else appColors.textLight
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            color = if (selectedTab == 0) TealAccent else appColors.textLight
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        Icon(
                            Icons.Default.EmojiEvents,
                            contentDescription = "Achievements",
                            tint = if (selectedTab == 1) TealAccent else appColors.textLight
                        )
                    },
                    label = {
                        Text(
                            "Achievements",
                            color = if (selectedTab == 1) TealAccent else appColors.textLight
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = if (selectedTab == 2) TealAccent else appColors.textLight
                        )
                    },
                    label = {
                        Text(
                            "Settings",
                            color = if (selectedTab == 2) TealAccent else appColors.textLight
                        )
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeWithNavigation(navController)
                1 -> AchievementsScreen(onNavigateBack = { selectedTab = 0 })
                2 -> SettingsScreen(onNavigateBack = { selectedTab = 0 })
            }
        }
    }
}

@Composable
fun HomeWithNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToAddHabit = {
                    navController.navigate("add_habit")
                },
                onNavigateToHabitDetail = { habitId ->
                    navController.navigate("habit_detail/$habitId")
                }
            )
        }

        composable("add_habit") {
            AddHabitScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("habit_detail/{habitId}") {
            HabitDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}