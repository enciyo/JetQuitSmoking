package com.enciyo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enciyo.data.dao.AccountDao
import com.enciyo.data.dao.PeriodDao
import com.enciyo.data.dao.TaskDao
import com.enciyo.data.dao.converters.LocalDateTimeConvert
import com.enciyo.data.entity.AccountEntity
import com.enciyo.data.entity.PeriodEntity
import com.enciyo.data.entity.TaskEntity

@Database(
    entities = [AccountEntity::class, TaskEntity::class, PeriodEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(
    LocalDateTimeConvert::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun taskDao(): TaskDao
    abstract fun periodDao(): PeriodDao
}