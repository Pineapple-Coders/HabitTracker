package com.app.habittracker.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun logHabitCreated(habitName: String) {
        val bundle = Bundle().apply {
            putString("habit_name", habitName)
        }
        firebaseAnalytics.logEvent(Constants.EVENT_HABIT_CREATED, bundle)
    }

    fun logHabitReset(habitName: String, streakDays: Int) {
        val bundle = Bundle().apply {
            putString("habit_name", habitName)
            putInt("streak_days", streakDays)
        }
        firebaseAnalytics.logEvent(Constants.EVENT_HABIT_RESET, bundle)
    }

    fun logHabitDeleted(habitName: String) {
        val bundle = Bundle().apply {
            putString("habit_name", habitName)
        }
        firebaseAnalytics.logEvent(Constants.EVENT_HABIT_DELETED, bundle)
    }

    fun logAchievementUnlocked(achievement: String, habitName: String) {
        val bundle = Bundle().apply {
            putString("achievement", achievement)
            putString("habit_name", habitName)
        }
        firebaseAnalytics.logEvent(Constants.EVENT_ACHIEVEMENT_UNLOCKED, bundle)
    }

    fun logScreenView(screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}