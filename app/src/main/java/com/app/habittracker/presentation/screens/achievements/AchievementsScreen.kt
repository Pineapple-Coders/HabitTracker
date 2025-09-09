package com.app.habittracker.presentation.screens.achievements

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.presentation.components.LoadingIndicator
import com.app.habittracker.presentation.theme.*
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AchievementsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Achievements",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(40.dp))
            }

            // Progress Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${uiState.unlockedCount} / ${uiState.totalCount}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Achievements Unlocked",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = if (uiState.totalCount > 0) {
                            uiState.unlockedCount.toFloat() / uiState.totalCount
                        } else 0f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isLoading) {
                LoadingIndicator()
            } else if (uiState.allAchievements.isEmpty()) {
                EmptyAchievements()
            } else {
                // Achievements List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.allAchievements) { achievementWithHabit ->
                        AchievementCard(achievementWithHabit)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AchievementCard(
    achievementWithHabit: AchievementWithHabit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (achievementWithHabit.isUnlocked) 1f else 0.95f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(animatedScale)
            .alpha(if (achievementWithHabit.isUnlocked) 1f else 0.6f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (achievementWithHabit.isUnlocked) CardBackground else IconBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (achievementWithHabit.isUnlocked) 3.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Achievement Icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        when (achievementWithHabit.level) {
                            AchievementLevel.FRESH_START -> Color(0xFF4CAF50)
                            AchievementLevel.COMMITTED -> Color(0xFF2196F3)
                            AchievementLevel.WARRIOR -> Color(0xFF9C27B0)
                            AchievementLevel.CHAMPION -> Color(0xFFFF9800)
                            AchievementLevel.MASTER -> Color(0xFFF44336)
                            AchievementLevel.LEGEND -> Color(0xFFFFD700)
                            AchievementLevel.TITAN -> Color(0xFF9E9E9E)
                        }.copy(alpha = if (achievementWithHabit.isUnlocked) 1f else 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (achievementWithHabit.level) {
                        AchievementLevel.FRESH_START -> Icons.Default.Star
                        AchievementLevel.COMMITTED -> Icons.Default.Grade
                        AchievementLevel.WARRIOR -> Icons.Default.Shield
                        AchievementLevel.CHAMPION -> Icons.Default.EmojiEvents
                        AchievementLevel.MASTER -> Icons.Default.School
                        AchievementLevel.LEGEND -> Icons.Default.Whatshot
                        AchievementLevel.TITAN -> Icons.Default.Diamond
                    },
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Achievement Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = achievementWithHabit.level.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (achievementWithHabit.isUnlocked) TextPrimary else TextSecondary
                )

                Text(
                    text = achievementWithHabit.habitName,
                    fontSize = 14.sp,
                    color = TextSecondary
                )

                Text(
                    text = achievementWithHabit.level.description,
                    fontSize = 12.sp,
                    color = TextSecondary.copy(alpha = 0.7f)
                )

                if (achievementWithHabit.isUnlocked && achievementWithHabit.achievement != null) {
                    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
                        .withZone(ZoneId.systemDefault())
                    Text(
                        text = "Unlocked: ${formatter.format(achievementWithHabit.achievement.unlockedAt.toJavaInstant())}",
                        fontSize = 11.sp,
                        color = Primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Lock/Unlock Icon
            if (!achievementWithHabit.isUnlocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    modifier = Modifier.size(20.dp),
                    tint = TextSecondary.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun EmptyAchievements() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = TextSecondary.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No achievements yet",
                fontSize = 16.sp,
                color = TextSecondary
            )

            Text(
                text = "Start tracking habits to unlock achievements",
                fontSize = 14.sp,
                color = TextSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}