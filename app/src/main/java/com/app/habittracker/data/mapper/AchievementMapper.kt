package com.app.habittracker.data.mapper

import com.app.habittracker.data.local.database.entity.AchievementEntity
import com.app.habittracker.domain.model.Achievement
import com.app.habittracker.domain.model.AchievementLevel

fun AchievementEntity.toDomain(): Achievement {
    return Achievement(
        id = id,
        habitId = habitId,
        level = AchievementLevel.valueOf(level),
        unlockedAt = unlockedAt,
        isNotified = isNotified
    )
}

fun Achievement.toEntity(): AchievementEntity {
    return AchievementEntity(
        id = id,
        habitId = habitId,
        level = level.name,
        unlockedAt = unlockedAt,
        isNotified = isNotified
    )
}