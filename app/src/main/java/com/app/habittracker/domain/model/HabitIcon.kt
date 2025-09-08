package com.app.habittracker.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class HabitIcon(val iconName: String, val icon: ImageVector) {
    SMOKING("Smoking", Icons.Default.SmokingRooms),
    DRINKING("Drinking", Icons.Default.LocalBar),
    JUNK_FOOD("Junk Food", Icons.Default.Fastfood),
    GAMING("Gaming", Icons.Default.SportsEsports),
    COFFEE("Coffee", Icons.Default.Coffee),
    SOCIAL_MEDIA("Social Media", Icons.Default.PhoneAndroid),
    SHOPPING("Shopping", Icons.Default.ShoppingCart),
    PROCRASTINATION("Procrastination", Icons.Default.Schedule),
    SUGAR("Sugar", Icons.Default.Cake),
    LATE_NIGHT("Late Night", Icons.Default.Bedtime),
    NAIL_BITING("Nail Biting", Icons.Default.PanTool),
    OVERSLEEPING("Oversleeping", Icons.Default.Hotel),
    SCREEN_TIME("Screen Time", Icons.Default.ScreenLockPortrait),
    NEGATIVE_THINKING("Negative Thinking", Icons.Default.Psychology),
    CUSTOM("Custom", Icons.Default.Star);

    companion object {
        fun fromIconName(name: String): HabitIcon {
            return values().find { it.iconName == name } ?: CUSTOM
        }
    }
}