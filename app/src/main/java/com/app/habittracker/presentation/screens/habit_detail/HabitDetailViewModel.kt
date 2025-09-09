package com.app.habittracker.presentation.screens.habit_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.domain.usecase.achievement.CheckAchievementsUseCase
import com.app.habittracker.domain.usecase.habit.DeleteHabitUseCase
import com.app.habittracker.domain.usecase.habit.GetHabitByIdUseCase
import com.app.habittracker.domain.usecase.habit.ResetHabitUseCase
import com.app.habittracker.domain.usecase.timer.CalculateStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val resetHabitUseCase: ResetHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val calculateStreakUseCase: CalculateStreakUseCase,
    private val checkAchievementsUseCase: CheckAchievementsUseCase
) : ViewModel() {

    private val habitId: Long = checkNotNull(savedStateHandle["habitId"])

    private val _uiState = MutableStateFlow(HabitDetailUiState())
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        loadHabit()
        startTimer()
    }

    private fun loadHabit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val habit = getHabitByIdUseCase(habitId)
            if (habit != null) {
                updateStreakInfo(habit)
                // Check for new achievements
                val newAchievements = checkAchievementsUseCase(habit)
                // Handle new achievements if any
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Update every second
                _uiState.value.habit?.let { habit ->
                    updateStreakInfo(habit)
                }
            }
        }
    }

    private fun updateStreakInfo(habit: com.app.habittracker.domain.model.Habit) {
        val streakInfo = calculateStreakUseCase(habit)
        val longestStreakSeconds = habit.longestStreak
        val longestDays = (longestStreakSeconds / 86400).toInt()
        val longestHours = ((longestStreakSeconds % 86400) / 3600).toInt()

        _uiState.update { state ->
            state.copy(
                habit = habit,
                currentStreakDays = streakInfo.daysClean,
                currentStreakHours = streakInfo.hoursClean,
                currentStreakMinutes = streakInfo.minutesClean,
                longestStreakDays = longestDays,
                longestStreakHours = longestHours,
                resetHistory = habit.resetHistory
            )
        }
    }

    fun showResetDialog() {
        _uiState.update { it.copy(showResetDialog = true) }
    }

    fun hideResetDialog() {
        _uiState.update { it.copy(showResetDialog = false) }
    }

    fun showDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun hideDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun resetHabit() {
        viewModelScope.launch {
            resetHabitUseCase(habitId)
            _uiState.update {
                it.copy(
                    showResetDialog = false,
                    showEncouragementMessage = getRandomEncouragementMessage()
                )
            }
            loadHabit()

            // Hide encouragement message after 3 seconds
            delay(3000)
            _uiState.update { it.copy(showEncouragementMessage = null) }
        }
    }

    fun deleteHabit() {
        viewModelScope.launch {
            _uiState.value.habit?.let { habit ->
                deleteHabitUseCase(habit)
                _navigationEvent.emit(NavigationEvent.NavigateBack)
            }
        }
    }

    private fun getRandomEncouragementMessage(): String {
        val messages = listOf(
            "Every setback is a setup for a comeback!",
            "You're not starting over, you're starting with experience.",
            "Progress, not perfection. Keep going!",
            "Tomorrow is a new day with new strength.",
            "You've got this! One day at a time.",
            "Falling down is human, getting up is heroic.",
            "Your journey continues. Stay strong!"
        )
        return messages.random()
    }

    sealed class NavigationEvent {
        object NavigateBack : NavigationEvent()
    }
}