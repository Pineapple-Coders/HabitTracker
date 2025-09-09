package com.app.habittracker.domain.usecase.quote

import com.app.habittracker.domain.model.Quote
import com.app.habittracker.domain.repository.QuoteRepository
import javax.inject.Inject

class GetDailyQuoteUseCase @Inject constructor(
    private val quoteRepository: QuoteRepository
) {
    suspend operator fun invoke(): Quote {
        return quoteRepository.getDailyQuote()
    }
}