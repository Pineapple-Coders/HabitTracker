package com.app.habittracker.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StreakDisplay(
    label: String,
    days: Int,
    hours: Int,
    minutes: Int,
    modifier: Modifier = Modifier,
    isAnimated: Boolean = false
) {
    val animatedDays by animateIntAsState(
        targetValue = days,
        animationSpec = tween(durationMillis = 600)
    )
    val animatedHours by animateIntAsState(
        targetValue = hours,
        animationSpec = tween(durationMillis = 600, delayMillis = 200)
    )
    val animatedMinutes by animateIntAsState(
        targetValue = minutes,
        animationSpec = tween(durationMillis = 600, delayMillis = 400)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                /*.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(GradientStart, GradientEnd)
                    )
                )*/
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TimeBlock(
                        value = if (isAnimated) animatedDays else days,
                        unit = "days"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TimeBlock(
                        value = if (isAnimated) animatedHours else hours,
                        unit = "hours"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TimeBlock(
                        value = if (isAnimated) animatedMinutes else minutes,
                        unit = "mins"
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeBlock(
    value: Int,
    unit: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = unit,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}