package com.app.habittracker.domain.model

import com.app.habittracker.utils.Constants

enum class AchievementLevel(
    val title: String,
    val description: String,
    val daysRequired: Int,
    val motivationalMessage: String
) {
    FRESH_START(
        title = "Fresh Start",
        description = "1 day clean!",
        daysRequired = Constants.ACHIEVEMENT_FRESH_START,
        motivationalMessage = "Every journey begins with a single step. You've taken yours!"
    ),
    COMMITTED(
        title = "Committed",
        description = "3 days strong!",
        daysRequired = Constants.ACHIEVEMENT_COMMITTED,
        motivationalMessage = "Your commitment is showing. Keep building this momentum!"
    ),
    WARRIOR(
        title = "Warrior",
        description = "1 week conquered!",
        daysRequired = Constants.ACHIEVEMENT_WARRIOR,
        motivationalMessage = "You're a warrior! One week down, lifetime to go!"
    ),
    CHAMPION(
        title = "Champion",
        description = "1 month achieved!",
        daysRequired = Constants.ACHIEVEMENT_CHAMPION,
        motivationalMessage = "Champion status unlocked! You've proven your strength!"
    ),
    MASTER(
        title = "Master",
        description = "3 months mastered!",
        daysRequired = Constants.ACHIEVEMENT_MASTER,
        motivationalMessage = "You've mastered self-control. You're an inspiration!"
    ),
    LEGEND(
        title = "Legend",
        description = "6 months legendary!",
        daysRequired = Constants.ACHIEVEMENT_LEGEND,
        motivationalMessage = "Legendary achievement! You're rewriting your story!"
    ),
    TITAN(
        title = "Titan",
        description = "1 year triumphant!",
        daysRequired = Constants.ACHIEVEMENT_TITAN,
        motivationalMessage = "Titan status achieved! You're unstoppable!"
    );

    companion object {
        fun getByDays(days: Int): AchievementLevel? {
            return values().findLast { days >= it.daysRequired }
        }
    }
}