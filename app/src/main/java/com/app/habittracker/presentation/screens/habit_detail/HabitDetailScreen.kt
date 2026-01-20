package com.app.habittracker.presentation.screens.habit_detail

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.presentation.components.AchievementBadgeRow
import com.app.habittracker.presentation.components.CustomDialog
import com.app.habittracker.presentation.components.LargeCircularTimer
import com.app.habittracker.presentation.screens.home.calculateAchievementProgress
import com.app.habittracker.presentation.screens.home.getHabitGradient
import com.app.habittracker.presentation.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HabitDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: HabitDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is HabitDetailViewModel.NavigationEvent.NavigateBack -> onNavigateBack()
            }
        }
    }

    val appColors = LocalAppColors.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = appColors.textPrimary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                uiState.habit?.let { habit ->
                    Text(
                        text = habit.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = appColors.textPrimary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { viewModel.showDeleteDialog() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red.copy(alpha = 0.7f)
                    )
                }
            }

            uiState.habit?.let { habit ->
                val gradientColors = getHabitGradient(habit.icon)
                val progress = calculateAchievementProgress(uiState.currentStreakDays)
                val progressPercent = (progress * 100).toInt()

                // Large Circular Timer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LargeCircularTimer(
                        days = uiState.currentStreakDays,
                        hours = uiState.currentStreakHours,
                        minutes = uiState.currentStreakMinutes,
                        progress = progress,
                        gradientColors = gradientColors
                    )
                }

                // Progress to Next Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = appColors.cardBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = TealAccent
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Progress to next: $progressPercent%",
                            fontSize = 14.sp,
                            color = appColors.textPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.width(80.dp),
                            color = TealAccent,
                            trackColor = appColors.inputBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Notification Toggle Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = appColors.cardBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = TealAccent
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Get Notified",
                            fontSize = 14.sp,
                            color = appColors.textPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = true,
                            onCheckedChange = { },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = TealAccent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Achievement Badge Row
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    AchievementBadgeRow(currentDays = uiState.currentStreakDays)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Row (Current vs Longest)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = appColors.cardBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Current Streak",
                                fontSize = 12.sp,
                                color = appColors.textSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${uiState.currentStreakDays}d ${uiState.currentStreakHours}h",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = appColors.textPrimary
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Longest Streak",
                                fontSize = 12.sp,
                                color = appColors.textSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${uiState.longestStreakDays}d ${uiState.longestStreakHours}h",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = appColors.textPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Menu Items
                if (uiState.resetHistory.isNotEmpty()) {
                    MenuItemRow(
                        title = "Reset History",
                        subtitle = "${uiState.resetHistory.size} resets",
                        onClick = { },
                        appColors = appColors
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Reset Button - Coral Color
                Button(
                    onClick = { viewModel.showResetDialog() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ResetButtonColor
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Reset Streak",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Encouragement Message
        AnimatedVisibility(
            visible = uiState.showEncouragementMessage != null,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Card(
                modifier = Modifier.padding(20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TealAccent
                )
            ) {
                Text(
                    text = uiState.showEncouragementMessage ?: "",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // Reset Confirmation Dialog
    if (uiState.showResetDialog) {
        CustomDialog(
            title = "Reset Timer",
            message = "Are you sure you want to reset this habit timer? Your current streak will be saved to history.",
            confirmText = "Reset",
            dismissText = "Cancel",
            onConfirm = { viewModel.resetHabit() },
            onDismiss = { viewModel.hideResetDialog() }
        )
    }

    // Delete Confirmation Dialog
    if (uiState.showDeleteDialog) {
        CustomDialog(
            title = "Delete Habit",
            message = "Are you sure you want to delete this habit? This action cannot be undone.",
            confirmText = "Delete",
            dismissText = "Cancel",
            onConfirm = { viewModel.deleteHabit() },
            onDismiss = { viewModel.hideDeleteDialog() }
        )
    }
}

@Composable
fun MenuItemRow(
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    appColors: AppColors
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = appColors.cardBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = appColors.textPrimary
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = appColors.textSecondary
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = appColors.textSecondary
            )
        }
    }
}