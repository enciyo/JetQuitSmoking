package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.enciyo.data.entity.TaskEntity
import com.enciyo.data.entity.TaskWithPeriodsEntity

@Dao
interface TaskDao {

    @Query("Select * from task order By taskId ASC")
    suspend fun tasks(): List<TaskEntity>

    @Insert
    suspend fun insert(taskEntity: TaskEntity) : Long

    @Insert
    suspend fun insertAll(vararg taskEntity: TaskEntity): List<Long>

    @Transaction
    @Query("SELECT * FROM task where taskId == :id")
    suspend fun getTaskWithPeriods(id: Int): TaskWithPeriodsEntity

}