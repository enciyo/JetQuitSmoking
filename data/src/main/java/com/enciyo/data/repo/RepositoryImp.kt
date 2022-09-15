package com.enciyo.data.repo

import com.enciyo.data.SessionAlarmManager
import com.enciyo.data.source.LocalDataSource
import com.enciyo.domain.Repository
import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val sessionAlarmManager: SessionAlarmManager,
    @ApplicationScope private val appScope: CoroutineScope,
) : Repository {

    override fun isLoggedIn() = flow { emit(localDataSource.isLoggedIn()) }

    override fun saveAccount(account: Account) = flow { emit(localDataSource.save(account)) }

    override fun saveTasks(tasks: List<Task>) =
        flow { emit(localDataSource.saveAll(*tasks.toTypedArray())) }

    override fun savePeriods(periods: List<Period>) =
        flow {
            val today = today()
            val filteredPeriod = periods
                .filter {
                    it.time.date.isSameDay(today.date)
                }
            log("FilteredPeriod -> ${periods.size}")
            val period = filteredPeriod
                .onEach {
                    val compareResult = it.time.time.copyWithResetSecond()
                        .compareTo(today.time.copyWithResetSecond()) == 1
                    log("${it.time.time.copyWithResetSecond()} ${today.time.copyWithResetSecond()} $compareResult")
                }
                .find {
                    it.time.time.copyWithResetSecond()
                        .compareTo(today.time.copyWithResetSecond()) == 1
                }?.let { period ->
                    sessionAlarmManager.invoke(
                        period.taskId,
                        period.time,
                        period.taskId
                    )
                    log("period -> $period")
                }
            emit(localDataSource.saveAll(*periods.toTypedArray()))
        }

    override fun setNextAlarm() {
        val today = today()
        appScope.launch {
            val task = localDataSource.tasks().find { it.taskTime.date.isSameDay(today.date) }
                ?: return@launch
            val periods = localDataSource.taskPeriodsById(task.taskId)
            val period = periods.periods.find {
                it.time.time.copyWithResetSecond().compareTo(today.time.copyWithResetSecond()) == 1
            }
            sessionAlarmManager.invoke(
                task.taskId,
                period!!.time,
                task.taskId
            )
        }
    }

    override fun tasks(): Flow<List<Task>> = flow { emit(localDataSource.tasks()) }

    override fun taskPeriodsById(id: Int): Flow<TaskWithPeriods> =
        flow { emit(localDataSource.taskPeriodsById(id)) }

    override fun account(): Flow<Account> = flow { emit(localDataSource.account()) }
}


