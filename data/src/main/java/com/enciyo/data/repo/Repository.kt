package com.enciyo.data.repo

import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskWithPeriods
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun singUp(account: Account)
    suspend fun isLoggedIn(): Boolean
    suspend fun setNextAlarm()

    fun tasks(): Flow<List<Task>>
    fun taskPeriodsById(id: Int): Flow<TaskWithPeriods>
    fun account(): Flow<Account>
}
