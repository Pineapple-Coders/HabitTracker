package com.app.habittracker.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.habittracker.domain.usecase.habit.GetAllHabitsUseCase
import com.app.habittracker.domain.usecase.quote.GetDailyQuoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val getDailyQuoteUseCase: GetDailyQuoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHabits()
        loadDailyQuote()
    }

    private fun loadHabits() {
        viewModelScope.launch {
            getAllHabitsUseCase().collect { habits ->
                _uiState.update { it.copy(habits = habits) }
            }
        }
    }

    private fun loadDailyQuote() {
        viewModelScope.launch {
            try {
                val quote = getDailyQuoteUseCase()
                _uiState.update { it.copy(dailyQuote = quote) }
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}