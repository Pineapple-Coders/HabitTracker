package com.app.habittracker.data.local.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun toTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}