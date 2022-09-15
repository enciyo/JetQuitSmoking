package com.enciyo.data.repo

import com.enciyo.data.SessionAlarmManager
import com.enciyo.data.source.LocalDataSource
import com.enciyo.domain.Repository
import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val sessionAlarmManager: SessionAlarmManager,
) : Repository {

    override fun isLoggedIn() = flow { emit(localDataSource.isLoggedIn()) }

    override fun saveAccount(account: Account) = flow { emit(localDataSource.save(account)) }

    override fun saveTasks(tasks: List<Task>) =
        flow { emit(localDataSource.saveAll(*tasks.toTypedArray())) }

    override fun savePeriods(periods: List<Period>) =
        flow { emit(localDataSource.saveAll(*periods.toTypedArray())) }

    override suspend fun setNextAlarm() {}

    override fun tasks(): Flow<List<Task>> = flow { emit(localDataSource.tasks()) }

    override fun taskPeriodsById(id: Int): Flow<TaskWithPeriods> =
        flow { emit(localDataSource.taskPeriodsById(id)) }

    override fun account(): Flow<Account> = flow { emit(localDataSource.account()) }
}


