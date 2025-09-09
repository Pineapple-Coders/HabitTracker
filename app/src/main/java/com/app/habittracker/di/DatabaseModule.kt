package com.app.habittracker.di

import android.content.Context
import androidx.room.Room
import com.app.habittracker.data.local.database.HabitDatabase
import com.app.habittracker.data.local.database.dao.AchievementDao
import com.app.habittracker.data.local.database.dao.HabitDao
import com.app.habittracker.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHabitDatabase(
        @ApplicationContext context: Context
    ): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(database: HabitDatabase): HabitDao {
        return database.habitDao()
    }

    @Provides
    @Singleton
    fun provideAchievementDao(database: HabitDatabase): AchievementDao {
        return database.achievementDao()
    }
}