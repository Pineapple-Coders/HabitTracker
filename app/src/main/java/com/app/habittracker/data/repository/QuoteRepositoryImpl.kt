package com.app.habittracker.data.repository

import com.app.habittracker.domain.model.Quote
import com.app.habittracker.domain.repository.QuoteRepository
import com.app.habittracker.utils.QuoteProvider
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class QuoteRepositoryImpl @Inject constructor() : QuoteRepository {

    private val quotes = QuoteProvider.quotes

    override suspend fun getRandomQuote(): Quote {
        return quotes[Random.nextInt(quotes.size)]
    }

    override suspend fun getDailyQuote(): Quote {
        // Use day of year as seed for consistent daily quote
        val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
        val index = dayOfYear % quotes.size
        return quotes[index]
    }

    override fun getAllQuotes(): List<Quote> {
        return quotes
    }
}