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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.domain.model.HabitIcon
import com.app.habittracker.domain.usecase.timer.CalculateStreakUseCase
import com.app.habittracker.presentation.components.CircularProgressRing
import com.app.habittracker.presentation.components.EmptyStateView
import com.app.habittracker.presentation.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    onNavigateToAddHabit: () -> Unit,
    onNavigateToHabitDetail: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val calculateStreakUseCase = remember { CalculateStreakUseCase() }
    val appColors = LocalAppColors.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val currentDate = remember {
        SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(Date())
    }

    Scaffold(
        floatingActionButton = {
            if (uiState.habits.size < 8) {
                ExtendedFloatingActionButton(
                    onClick = onNavigateToAddHabit,
                    containerColor = TealAccent,
                    contentColor = Color.White,
                    modifier = Modifier.padding(bottom = if (isLandscape) 8.dp else 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Habit", fontWeight = FontWeight.SemiBold)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header with title and date
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = if (isLandscape) 48.dp else 24.dp,
                        vertical = if (isLandscape) 16.dp else 24.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Habit Timer",
                    fontSize = if (isLandscape) 24.sp else 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = appColors.textPrimary
                )
                Text(
                    text = currentDate,
                    fontSize = 14.sp,
                    color = appColors.textSecondary
                )
            }

            // Scrollable content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.habits.isEmpty()) {
                    EmptyStateView(
                        title = "No habits yet",
                        message = "Tap 'Add Habit' to start tracking",
                        buttonText = null,
                        onButtonClick = null
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = if (isLandscape) 48.dp else 24.dp,
                            end = if (isLandscape) 48.dp else 24.dp,
                            bottom = 80.dp  // Extra padding for FAB
                        ),
                        verticalArrangement = Arrangement.spacedBy(if (isLandscape) 12.dp else 16.dp)
                    ) {
                        // Daily Quote
                        uiState.dailyQuote?.let { quote ->
                            item {
                                NewQuoteCard(quote = quote, appColors = appColors)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        // Habit Cards
                        items(
                            items = uiState.habits,
                            key = { it.id }
                        ) { habit ->
                            GradientHabitCard(
                                habit = habit,
                                calculateStreakUseCase = calculateStreakUseCase,
                                onClick = { onNavigateToHabitDetail(habit.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewQuoteCard(
    quote: com.app.habittracker.domain.model.Quote,
    appColors: AppColors
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = QuoteCardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Quote icon on left
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f),
                tint = appColors.textSecondary
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Quote text in center
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quote.text,
                    fontSize = 14.sp,
                    color = appColors.textPrimary,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "— ${quote.author}",
                    fontSize = 12.sp,
                    color = appColors.textSecondary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Badge on right
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = QuoteBadgeBg
            ) {
                Text(
                    text = "Daily",
                    fontSize = 10.sp,
                    color = QuoteBadgeText,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun GradientHabitCard(
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

    val gradientColors = getHabitGradient(habit.icon)
    val achievementProgress = calculateAchievementProgress(streakInfo.daysClean)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradientColors))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon - white on gradient
                Icon(
                    imageVector = habit.icon.icon,
                    contentDescription = habit.name,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Name + Timer
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = buildString {
                            if (streakInfo.daysClean > 0) {
                                append("${streakInfo.daysClean} days ")
                            }
                            if (streakInfo.hoursClean > 0) {
                                append("${streakInfo.hoursClean} hours ")
                            }
                            if (streakInfo.daysClean == 0 && streakInfo.hoursClean == 0) {
                                append("${streakInfo.minutesClean} mins")
                            }
                        }.trim(),
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }

                // Progress Ring
                CircularProgressRing(
                    progress = achievementProgress,
                    size = 48.dp,
                    strokeWidth = 4.dp,
                    backgroundColor = ProgressRingBg,
                    progressColor = ProgressRingFg,
                    showPercentage = true
                )
            }
        }
    }
}

fun getHabitGradient(icon: HabitIcon): List<Color> {
    return when(icon) {
        HabitIcon.SMOKING -> listOf(SmokingGradientStart, SmokingGradientEnd)
        HabitIcon.DRINKING -> listOf(DrinkingGradientStart, DrinkingGradientEnd)
        HabitIcon.JUNK_FOOD -> listOf(JunkFoodGradientStart, JunkFoodGradientEnd)
        HabitIcon.GAMING -> listOf(GamingGradientStart, GamingGradientEnd)
        HabitIcon.COFFEE -> listOf(CoffeeGradientStart, CoffeeGradientEnd)
        HabitIcon.SOCIAL_MEDIA -> listOf(SocialMediaGradientStart, SocialMediaGradientEnd)
        HabitIcon.SHOPPING -> listOf(ShoppingGradientStart, ShoppingGradientEnd)
        else -> listOf(DefaultGradientStart, DefaultGradientEnd)
    }
}

fun calculateAchievementProgress(daysClean: Int): Float {
    val levels = listOf(1, 3, 7, 30, 90, 180, 365)
    val nextLevel = levels.firstOrNull { it > daysClean } ?: 365
    val prevLevel = levels.lastOrNull { it <= daysClean } ?: 0
    return if (nextLevel == prevLevel) 1f
    else ((daysClean - prevLevel).toFloat() / (nextLevel - prevLevel)).coerceIn(0f, 1f)
}