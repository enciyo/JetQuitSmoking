package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.TaskPeriods


@Dao
interface TaskPeriodsDao {

    @Query("Select * from tasks_period where taskId == :id")
    suspend fun taskPeriodsById(id: Int): TaskPeriods?

    @Insert
    suspend fun insert(data: TaskPeriods)

    @Insert
    suspend fun insertAll(vararg data: TaskPeriods)

}