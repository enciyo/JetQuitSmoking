package com.enciyo.data.source

import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods


interface LocalDataSource {
    suspend fun save(account: Account): Account
    suspend fun account(): Account
    suspend fun isLoggedIn(): Boolean
    suspend fun save(task: Task) : Task
    suspend fun saveAll(vararg task: Task) : List<Task>
    suspend fun saveAll(vararg period: Period)
    suspend fun tasks(): List<Task>
    suspend fun taskPeriodsById(id: Int): TaskWithPeriods
}

