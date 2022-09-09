package com.enciyo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enciyo.data.dao.AccountDao
import com.enciyo.data.dao.PeriodDao
import com.enciyo.data.dao.TaskDao
import com.enciyo.data.dao.converters.LocalDateTimeConvert
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Period
import com.enciyo.data.entity.Task

@Database(
    entities = [Account::class, Task::class, Period::class],
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