package com.enciyo.data.source

import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods


interface LocalDataSource {
    suspend fun save(account: Account)
    suspend fun isLoggedIn(): Boolean
    suspend fun save(task: Task)
    suspend fun saveAll(vararg task: Task)
    suspend fun account(): Account
    suspend fun tasks(): List<Task>
    suspend fun saveAll(vararg taskPeriods: TaskPeriods)
    suspend fun taskPeriodsById(id: Int): TaskPeriods
}

