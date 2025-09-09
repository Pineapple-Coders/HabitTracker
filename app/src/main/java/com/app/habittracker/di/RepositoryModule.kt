package com.app.habittracker.di

import com.app.habittracker.data.repository.AchievementRepositoryImpl
import com.app.habittracker.data.repository.HabitRepositoryImpl
import com.app.habittracker.data.repository.QuoteRepositoryImpl
import com.app.habittracker.domain.repository.AchievementRepository
import com.app.habittracker.domain.repository.HabitRepository
import com.app.habittracker.domain.repository.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        habitRepositoryImpl: HabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindAchievementRepository(
        achievementRepositoryImpl: AchievementRepositoryImpl
    ): AchievementRepository

    @Binds
    @Singleton
    abstract fun bindQuoteRepository(
        quoteRepositoryImpl: QuoteRepositoryImpl
    ): QuoteRepository
}