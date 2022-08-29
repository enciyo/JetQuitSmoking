package com.enciyo.data.repo

import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods

interface Repository {
    suspend fun singUp(account: Account)
    suspend fun isLoggedIn(): Boolean
    suspend fun tasks(): List<Task>
    suspend fun taskPeriodsById(id: Int): TaskPeriods
    suspend fun account(): Account
}