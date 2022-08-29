package com.enciyo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enciyo.data.dao.AccountDao
import com.enciyo.data.dao.TaskDao
import com.enciyo.data.dao.TaskPeriodsDao
import com.enciyo.data.dao.converters.TaskPeriodsConverter
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods

@Database(
    entities = [Account::class, Task::class, TaskPeriods::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(TaskPeriodsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun taskDao(): TaskDao
    abstract fun taskPeriodsDao(): TaskPeriodsDao
}