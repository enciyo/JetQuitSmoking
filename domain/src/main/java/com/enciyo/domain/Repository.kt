package com.enciyo.domain

import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun saveAccount(account: Account): Flow<Account>
    fun isLoggedIn(): Flow<Boolean>
    fun saveTasks(tasks: List<Task>): Flow<List<Task>>
    fun savePeriods(periods: List<Period>): Flow<List<Period>>
    fun tasks(): Flow<List<Task>>
    fun taskPeriodsById(id: Int): Flow<TaskWithPeriods>
    suspend fun setNextAlarm()
    fun account(): Flow<Account>

}
