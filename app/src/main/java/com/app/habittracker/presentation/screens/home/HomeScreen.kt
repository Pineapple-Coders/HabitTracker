package com.app.habittracker.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.domain.usecase.timer.CalculateStreakUseCase
import com.app.habittracker.presentation.components.EmptyStateView
import com.app.habittracker.presentation.components.QuoteCard
import com.app.habittracker.presentation.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddHabit: () -> Unit,
    onNavigateToHabitDetail: (Long) -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val calculateStreakUseCase = remember { CalculateStreakUseCase() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Habit Timer",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onAchievementsClick = onNavigateToAchievements,
                onSettingsClick = onNavigateToSettings
            )
        },
        floatingActionButton = {
            if (uiState.habits.size < 8) {
                FloatingActionButton(
                    onClick = onNavigateToAddHabit,
                    containerColor = Primary,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Habit",
                        tint = Color.White
                    )
                }
            }
        },
        containerColor = Background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.habits.isEmpty()) {
                EmptyStateView(
                    title = "No habits yet",
                    message = "Tap the + button to add your first habit",
                    buttonText = null,
                    onButtonClick = null
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Daily Quote
                    uiState.dailyQuote?.let { quote ->
                        item {
                            QuoteCard(quote = quote)
                        }
                    }

                    // Habits
                    items(
                        items = uiState.habits,
                        key = { it.id }
                    ) { habit ->
                        HabitCardRedesigned(
                            habit = habit,
                            calculateStreakUseCase = calculateStreakUseCase,
                            onClick = { onNavigateToHabitDetail(habit.id) }
                        )
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    onAchievementsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Primary
                )
            },
            label = {
                Text(
                    "Home",
                    color = Primary
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onAchievementsClick,
            icon = {
                Icon(
                    Icons.Default.EmojiEvents,
                    contentDescription = "Achievements",
                    tint = TextSecondary
                )
            },
            label = {
                Text(
                    "Achievements",
                    color = TextSecondary
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onSettingsClick,
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = TextSecondary
                )
            },
            label = {
                Text(
                    "Settings",
                    color = TextSecondary
                )
            }
        )
    }
}

@Composable
fun HabitCardRedesigned(
    habit: com.app.habittracker.domain.model.Habit,
    calculateStreakUseCase: CalculateStreakUseCase,
    onClick: () -> Unit
) {
    var currentTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }

    val streakInfo = remember(currentTime) {
        calculateStreakUseCase(habit)
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        when(habit.icon.iconName) {
                            "Smoking" -> SmokingIconBg
                            "Drinking" -> DrinkingIconBg
                            "Junk Food" -> JunkFoodIconBg
                            "Gaming" -> GamingIconBg
                            else -> IconBackground
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = habit.icon.icon,
                    contentDescription = habit.name,
                    modifier = Modifier.size(24.dp),
                    tint = TextPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = habit.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = buildString {
                        append("${streakInfo.daysClean}d ")
                        append("${streakInfo.hoursClean}h ")
                        append("${streakInfo.minutesClean}m")
                    },
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Details",
                modifier = Modifier.size(24.dp),
                tint = TextSecondary
            )
        }
    }
}