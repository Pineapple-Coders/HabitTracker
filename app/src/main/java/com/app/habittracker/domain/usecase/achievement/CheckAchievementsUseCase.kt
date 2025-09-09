package com.app.habittracker.domain.usecase.achievement

import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.repository.AchievementRepository
import com.app.habittracker.domain.usecase.timer.CalculateStreakUseCase
import com.app.habittracker.utils.Constants
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class CheckAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val calculateStreakUseCase: CalculateStreakUseCase,
    private val analytics: FirebaseAnalytics
) {
    suspend operator fun invoke(habit: Habit): List<AchievementLevel> {
        val streakInfo = calculateStreakUseCase(habit)
        val daysClean = streakInfo.daysClean

        val unlockedAchievements = mutableListOf<AchievementLevel>()

        AchievementLevel.values().forEach { level ->
            if (daysClean >= level.daysRequired) {
                val wasNewlyUnlocked = achievementRepository.unlockAchievement(habit.id, level)
                if (wasNewlyUnlocked) {
                    unlockedAchievements.add(level)

                    // Log to Firebase Analytics
                    analytics.logEvent(Constants.EVENT_ACHIEVEMENT_UNLOCKED, null)
                }
            }
        }

        return unlockedAchievements
    }
}