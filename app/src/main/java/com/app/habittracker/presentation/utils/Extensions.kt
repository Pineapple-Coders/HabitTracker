package com.app.habittracker.presentation.utils

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration

fun Duration.toReadableString(): String {
    val days = inWholeDays
    val hours = inWholeHours % 24
    val minutes = inWholeMinutes % 60

    return when {
        days > 0 -> "$days days, $hours hours"
        hours > 0 -> "$hours hours, $minutes minutes"
        else -> "$minutes minutes"
    }
}

fun Long.toFormattedDate(pattern: String = "MMM dd, yyyy"): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}

fun Int.toOrdinal(): String {
    val suffixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    return when (this % 100) {
        11, 12, 13 -> "${this}th"
        else -> "$this${suffixes[this % 10]}"
    }
}

@Composable
fun Modifier.shimmerEffect(): Modifier {
    // Implement shimmer effect if needed
    return this
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideInAnimation(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeOut(),
        content = content
    )
}