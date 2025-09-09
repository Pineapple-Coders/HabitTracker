package com.app.habittracker.presentation.screens.add_habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.domain.model.Habit
import com.app.habittracker.domain.model.HabitIcon
import com.app.habittracker.domain.usecase.habit.AddHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddHabitUiState())
    val uiState: StateFlow<AddHabitUiState> = _uiState.asStateFlow()

    fun updateHabitName(name: String) {
        _uiState.update { it.copy(habitName = name, errorMessage = null) }
    }

    fun updateSelectedIcon(icon: HabitIcon) {
        _uiState.update { it.copy(selectedIcon = icon) }
    }

    fun saveHabit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val habit = Habit(
                name = _uiState.value.habitName.trim(),
                icon = _uiState.value.selectedIcon,
                startDate = Clock.System.now()
            )

            when (val result = addHabitUseCase(habit)) {
                is AddHabitUseCase.Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSaved = true
                        )
                    }
                }
                is AddHabitUseCase.Result.InvalidName -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Please enter a valid habit name"
                        )
                    }
                }
                is AddHabitUseCase.Result.MaxHabitsReached -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Maximum 8 habits allowed"
                        )
                    }
                }
            }
        }
    }
}