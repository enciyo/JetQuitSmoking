package com.enciyo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enciyo.data.entity.Task

@Dao
interface TaskDao {

    @Query("Select * from task order By taskId ASC")
    suspend fun tasks(): List<Task>

    @Insert
    suspend fun insert(task: Task)

    @Insert
    suspend fun insertAll(vararg task: Task)

}