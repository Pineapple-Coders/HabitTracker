package com.app.habittracker.domain.usecase.achievement

import com.app.habittracker.domain.model.Achievement
import com.app.habittracker.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAchievementsUseCase @Inject constructor(
    private val achievementRepository: AchievementRepository
) {
    operator fun invoke(): Flow<List<Achievement>> {
        return achievementRepository.getAllAchievements()
    }
}