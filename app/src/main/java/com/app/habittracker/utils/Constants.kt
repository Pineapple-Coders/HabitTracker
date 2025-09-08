package com.app.habittracker.utils

object Constants {
    // Notification Channels
    const val ACHIEVEMENT_CHANNEL_ID = "achievement_channel"
    const val QUOTE_CHANNEL_ID = "quote_channel"
    const val REMINDER_CHANNEL_ID = "reminder_channel"

    // DataStore
    const val PREFERENCES_NAME = "habit_tracker_preferences"
    const val THEME_MODE_KEY = "theme_mode"
    const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
    const val DAILY_QUOTES_ENABLED_KEY = "daily_quotes_enabled"

    // Database
    const val DATABASE_NAME = "habit_tracker_database"

    // Habits
    const val MAX_HABITS = 8

    // Achievement Milestones (in days)
    const val ACHIEVEMENT_FRESH_START = 1
    const val ACHIEVEMENT_COMMITTED = 3
    const val ACHIEVEMENT_WARRIOR = 7
    const val ACHIEVEMENT_CHAMPION = 30
    const val ACHIEVEMENT_MASTER = 90
    const val ACHIEVEMENT_LEGEND = 180
    const val ACHIEVEMENT_TITAN = 365

    // Firebase Analytics Events
    const val EVENT_HABIT_CREATED = "habit_created"
    const val EVENT_HABIT_RESET = "habit_reset"
    const val EVENT_HABIT_DELETED = "habit_deleted"
    const val EVENT_ACHIEVEMENT_UNLOCKED = "achievement_unlocked"
    const val EVENT_APP_THEME_CHANGED = "app_theme_changed"
}