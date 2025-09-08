package com.app.habittracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.app.habittracker.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class HabitTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)

        // Create notification channels
        createNotificationChannels()

        // Schedule daily quote worker
     //   scheduleDailyQuoteWorker()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            val channels = listOf(
                NotificationChannel(
                    Constants.ACHIEVEMENT_CHANNEL_ID,
                    "Achievements",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifications for unlocked achievements"
                    enableVibration(true)
                },
                NotificationChannel(
                    Constants.QUOTE_CHANNEL_ID,
                    "Daily Motivation",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Daily motivational quotes"
                },
                NotificationChannel(
                    Constants.REMINDER_CHANNEL_ID,
                    "Habit Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Habit tracking reminders"
                }
            )

            notificationManager.createNotificationChannels(channels)
        }
    }

  /*  private fun scheduleDailyQuoteWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val dailyQuoteRequest = PeriodicWorkRequestBuilder<DailyQuoteWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_quote_work",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyQuoteRequest
        )
    }*/

    private fun calculateInitialDelay(): Long {
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = now
            set(java.util.Calendar.HOUR_OF_DAY, 9) // 9 AM
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
            if (timeInMillis <= now) {
                add(java.util.Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - now
    }
}