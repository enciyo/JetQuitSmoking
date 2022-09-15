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

    companion object {
        const val TASK_COUNT = 21
        const val DAILY_MINUTES = 900
    }

    override suspend fun isLoggedIn(): Boolean =
        localDataSource.isLoggedIn()


    override fun saveAccount(account: Account) = flow {
        emit(localDataSource.save(account))
    }

    override fun saveTasks(tasks: List<Task>) = flow {
        emit(localDataSource.saveAll(*tasks.toTypedArray()))
    }

    override suspend fun savePeriods(periods: List<Period>) {
        localDataSource.saveAll(*periods.toTypedArray())
    }

    override suspend fun setNextAlarm() {
        //TODO("Create alarm for next sesion")
    }

    override fun tasks(): Flow<List<Task>> =
        flow { emit(localDataSource.tasks()) }

    override fun taskPeriodsById(id: Int): Flow<TaskWithPeriods> =
        flow { emit(localDataSource.taskPeriodsById(id)) }


    override fun account(): Flow<Account> =
        flow { emit(localDataSource.account()) }
}


