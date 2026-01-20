package com.app.habittracker.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.habittracker.presentation.theme.DrinkingGradientEnd
import com.app.habittracker.presentation.theme.DrinkingGradientStart
import com.app.habittracker.presentation.theme.TextSecondary

@Composable
fun LargeCircularTimer(
    days: Int,
    hours: Int,
    minutes: Int,
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    gradientColors: List<Color> = listOf(DrinkingGradientStart, DrinkingGradientEnd)
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Gradient ring background
        Canvas(modifier = Modifier.size(size)) {
            val strokeWidthPx = 8.dp.toPx()
            val arcSize = this.size.minDimension - strokeWidthPx

            // Background ring (light)
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize),
                topLeft = androidx.compose.ui.geometry.Offset(
                    (this.size.width - arcSize) / 2,
                    (this.size.height - arcSize) / 2
                )
            )

            // Progress arc with gradient
            drawArc(
                brush = Brush.sweepGradient(gradientColors),
                startAngle = -90f,
                sweepAngle = progress.coerceIn(0f, 1f) * 360f,
                useCenter = false,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize),
                topLeft = androidx.compose.ui.geometry.Offset(
                    (this.size.width - arcSize) / 2,
                    (this.size.height - arcSize) / 2
                )
            )
        }

        // Plus icon at top (12 o'clock position)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-4).dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(gradientColors)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Progress indicator",
                modifier = Modifier.size(16.dp),
                tint = Color.White
            )
        }

        // Center content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Days display
            Text(
                text = buildString {
                    if (days > 0) {
                        append("$days ")
                        append(if (days == 1) "DAY" else "DAYS")
                    } else {
                        append("0 DAYS")
                    }
                },
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Hours and minutes
            Text(
                text = buildString {
                    append("$hours ")
                    append(if (hours == 1) "HOUR" else "HOURS")
                    append(" ")
                    append("$minutes ")
                    append(if (minutes == 1) "MIN" else "MINS")
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
