package com.app.habittracker.data.local.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.habittracker.data.local.database.converter.DateConverter
import com.app.habittracker.data.local.database.dao.AchievementDao
import com.app.habittracker.data.local.database.dao.HabitDao
import com.app.habittracker.data.local.database.entity.AchievementEntity
import com.app.habittracker.data.local.database.entity.HabitEntity
import com.app.habittracker.data.local.database.entity.ResetHistoryEntity
import com.app.habittracker.utils.Constants

@Database(
    entities = [
        HabitEntity::class,
        ResetHistoryEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}