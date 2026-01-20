package com.app.habittracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.presentation.theme.*

@Composable
fun AchievementBadgeRow(
    currentDays: Int,
    modifier: Modifier = Modifier
) {
    val achievementLevels = AchievementLevel.values().toList()

    Column(modifier = modifier) {
        Text(
            text = "Achievements",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            achievementLevels.forEach { level ->
                val isUnlocked = currentDays >= level.daysRequired
                AchievementBadgeItem(
                    level = level,
                    isUnlocked = isUnlocked
                )
            }
        }
    }
}

@Composable
private fun AchievementBadgeItem(
    level: AchievementLevel,
    isUnlocked: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(64.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(
                    if (isUnlocked) getBadgeColor(level) else AchievementLocked
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isUnlocked) {
                Icon(
                    imageVector = getBadgeIcon(level),
                    contentDescription = level.title,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = level.title,
            fontSize = 10.sp,
            fontWeight = if (isUnlocked) FontWeight.Medium else FontWeight.Normal,
            color = if (isUnlocked) TextPrimary else TextSecondary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${level.daysRequired}d",
            fontSize = 9.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

private fun getBadgeColor(level: AchievementLevel): Color {
    return when (level) {
        AchievementLevel.FRESH_START -> AchievementBronze
        AchievementLevel.COMMITTED -> AchievementBronze
        AchievementLevel.WARRIOR -> AchievementSilver
        AchievementLevel.CHAMPION -> AchievementGold
        AchievementLevel.MASTER -> AchievementGold
        AchievementLevel.LEGEND -> AchievementGold
        AchievementLevel.TITAN -> AchievementGold
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
