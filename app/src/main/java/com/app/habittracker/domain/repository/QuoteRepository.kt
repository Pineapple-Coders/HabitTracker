package com.app.habittracker.domain.repository

import com.app.habittracker.domain.model.Quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote
    suspend fun getDailyQuote(): Quote
    fun getAllQuotes(): List<Quote>
}