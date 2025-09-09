package com.app.habittracker.presentation.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.app.habittracker.MainActivity
import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.utils.Constants

object NotificationHelper {

    fun showAchievementNotification(
        context: Context,
        achievement: AchievementLevel,
        habitName: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.ACHIEVEMENT_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.star_on)
            .setContentTitle("🎉 Achievement Unlocked!")
            .setContentText("${achievement.title} - ${achievement.description}")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("$habitName: ${achievement.motivationalMessage}"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(achievement.ordinal, notification)
    }
}