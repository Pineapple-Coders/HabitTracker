package com.app.habittracker.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import com.app.habittracker.MainActivity
import com.app.habittracker.data.local.database.HabitDatabase
import com.app.habittracker.data.mapper.toDomain
import com.app.habittracker.domain.model.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SmallHabitWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Single

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val habit = withContext(Dispatchers.IO) {
            try {
                val database = HabitDatabase.getInstance(context)
                val habits = database.habitDao().getAllHabits().first()
                habits.firstOrNull()?.toDomain()
            } catch (e: Exception) {
                null
            }
        }

        provideContent {
            SmallWidgetContent(context = LocalContext.current, habit = habit)
        }
    }
}

@Composable
private fun SmallWidgetContent(context: Context, habit: Habit?) {
    val tealColor = ColorProvider(Color(0xFF26D0CE))
    val textPrimaryColor = ColorProvider(Color(0xFF2C3E50))
    val textSecondaryColor = ColorProvider(Color(0xFF7F8C8D))

    val intent = Intent(context, MainActivity::class.java)

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(16.dp)
            .background(ColorProvider(Color.White))
            .clickable(actionStartActivity(intent))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (habit != null) {
            val streakInfo = calculateStreak(habit)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Habit icon placeholder (using text)
                Text(
                    text = getIconEmoji(habit.icon.iconName),
                    style = TextStyle(fontSize = 28.sp)
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Habit name
                Text(
                    text = habit.name,
                    style = TextStyle(
                        color = textPrimaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )

                Spacer(modifier = GlanceModifier.height(4.dp))

                // Streak time
                Text(
                    text = formatStreak(streakInfo),
                    style = TextStyle(
                        color = tealColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = GlanceModifier.height(2.dp))

                Text(
                    text = "streak",
                    style = TextStyle(
                        color = textSecondaryColor,
                        fontSize = 10.sp
                    )
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "+",
                    style = TextStyle(
                        color = tealColor,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = "Add Habit",
                    style = TextStyle(
                        color = textSecondaryColor,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

private fun getIconEmoji(iconName: String): String {
    return when (iconName) {
        "Smoking" -> "\uD83D\uDEAD"
        "Drinking" -> "\uD83C\uDF7A"
        "Junk Food" -> "\uD83C\uDF54"
        "Gaming" -> "\uD83C\uDFAE"
        "Coffee" -> "\u2615"
        "Social Media" -> "\uD83D\uDCF1"
        "Shopping" -> "\uD83D\uDED2"
        else -> "\u2B50"
    }
}

data class StreakInfo(val days: Int, val hours: Int, val minutes: Int)

private fun calculateStreak(habit: Habit): StreakInfo {
    val startTime = habit.lastResetDate?.toEpochMilliseconds() ?: habit.startDate.toEpochMilliseconds()
    val currentTime = System.currentTimeMillis()
    val diffSeconds = (currentTime - startTime) / 1000

    val days = (diffSeconds / 86400).toInt()
    val hours = ((diffSeconds % 86400) / 3600).toInt()
    val minutes = ((diffSeconds % 3600) / 60).toInt()

    return StreakInfo(days, hours, minutes)
}

private fun formatStreak(streak: StreakInfo): String {
    return when {
        streak.days > 0 -> "${streak.days}d ${streak.hours}h"
        streak.hours > 0 -> "${streak.hours}h ${streak.minutes}m"
        else -> "${streak.minutes}m"
    }
}

class SmallHabitWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SmallHabitWidget()
}
