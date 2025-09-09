package com.app.habittracker.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.habittracker.domain.model.AchievementLevel

@Composable
fun AchievementBadge(
    level: AchievementLevel,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .size(80.dp)
            .scale(if (isUnlocked) scale else 1f)
            .rotate(if (isUnlocked) rotation else 0f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    getBadgeColor(level).copy(
                        alpha = if (isUnlocked) 1f else 0.3f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = getBadgeIcon(level),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
                Text(
                    text = level.daysRequired.toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

private fun getBadgeColor(level: AchievementLevel): Color {
    return when (level) {
        AchievementLevel.FRESH_START -> Color(0xFF4CAF50)
        AchievementLevel.COMMITTED -> Color(0xFF2196F3)
        AchievementLevel.WARRIOR -> Color(0xFF9C27B0)
        AchievementLevel.CHAMPION -> Color(0xFFFF9800)
        AchievementLevel.MASTER -> Color(0xFFF44336)
        AchievementLevel.LEGEND -> Color(0xFFFFD700)
        AchievementLevel.TITAN -> Color(0xFF9E9E9E)
    }
}

private fun getBadgeIcon(level: AchievementLevel) = when (level) {
    AchievementLevel.FRESH_START -> Icons.Default.Star
    AchievementLevel.COMMITTED -> Icons.Default.Grade
    AchievementLevel.WARRIOR -> Icons.Default.Shield
    AchievementLevel.CHAMPION -> Icons.Default.EmojiEvents
    AchievementLevel.MASTER -> Icons.Default.School
    AchievementLevel.LEGEND -> Icons.Default.Whatshot
    AchievementLevel.TITAN -> Icons.Default.Diamond
}