package com.app.habittracker.presentation.screens.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.domain.model.AchievementLevel
import com.app.habittracker.domain.usecase.achievement.GetAllAchievementsUseCase
import com.app.habittracker.domain.usecase.habit.GetAllHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getAllAchievementsUseCase: GetAllAchievementsUseCase,
    private val getAllHabitsUseCase: GetAllHabitsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState: StateFlow<AchievementsUiState> = _uiState.asStateFlow()

    init {
        loadAchievements()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            combine(
                getAllAchievementsUseCase(),
                getAllHabitsUseCase()
            ) { achievements, habits ->
                val achievementWithHabits = mutableListOf<AchievementWithHabit>()

                habits.forEach { habit ->
                    AchievementLevel.values().forEach { level ->
                        val achievement = achievements.find {
                            it.habitId == habit.id && it.level == level
                        }

                        achievementWithHabits.add(
                            AchievementWithHabit(
                                achievement = achievement,
                                level = level,
                                habitName = habit.name,
                                isUnlocked = achievement != null
                            )
                        )
                    }
                }

                _uiState.update { state ->
                    state.copy(
                        allAchievements = achievementWithHabits.sortedBy { !it.isUnlocked },
                        unlockedCount = achievementWithHabits.count { it.isUnlocked },
                        totalCount = achievementWithHabits.size,
                        isLoading = false
                    )
                }
            }.collect()
        }
    }
}