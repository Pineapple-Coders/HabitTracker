package com.app.habittracker.data.mapper

import com.app.habittracker.data.local.database.entity.HabitEntity
import com.app.habittracker.data.local.database.entity.ResetHistoryEntity
import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.HabitIcon
import com.app.habittracker.domain.model.ResetHistory

fun HabitEntity.toDomain(resetHistory: List<ResetHistory> = emptyList()): Habit {
    return Habit(
        id = id,
        name = name,
        icon = HabitIcon.fromIconName(iconName),
        startDate = startDate,
        lastResetDate = lastResetDate,
        longestStreak = longestStreak,
        resetHistory = resetHistory,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        iconName = icon.iconName,
        startDate = startDate,
        lastResetDate = lastResetDate,
        longestStreak = longestStreak,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun ResetHistoryEntity.toDomain(): ResetHistory {
    return ResetHistory(
        id = id,
        habitId = habitId,
        resetDate = resetDate,
        streakDuration = streakDuration
    )
}

fun ResetHistory.toEntity(): ResetHistoryEntity {
    return ResetHistoryEntity(
        id = id,
        habitId = habitId,
        resetDate = resetDate,
        streakDuration = streakDuration
    )
}