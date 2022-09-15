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
import kotlinx.coroutines.flow.onCompletion
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
            emit(localDataSource.saveAll(*periods.toTypedArray()))
        }
            .onCompletion {
                scheduleFirstNotification(periods)
            }

    private fun scheduleFirstNotification(periods: List<Period>) {
        val today = today()
        val sameDayPeriods = periods.filter { it.time.date.isSameDay(today.date) }
        val index = sameDayPeriods.indexOfFirst {
            it.time.time.copyWithResetSecond().compareTo(today.time.copyWithResetSecond()) == 1
        }
        val period = sameDayPeriods.getOrNull(index) ?: return
        log("$index")
        sessionAlarmManager.invoke(
            taskId = period.taskId,
            time = period.time,
            smokeCount = index
        )
    }


    override fun setNextAlarm() {
        val today = today()
        appScope.launch {
            val sameDayTasks = localDataSource.tasks().find { it.taskTime.date.isSameDay(today.date) } ?: return@launch
            val periods = localDataSource.taskPeriodsById(sameDayTasks.taskId)
            val index = periods.periods.indexOfFirst {
                it.time.time.copyWithResetSecond().compareTo(today.time.copyWithResetSecond()) == 1
            }
            val period = periods.periods.getOrNull(index)?:return@launch
            log("$index")
            sessionAlarmManager.invoke(
                sameDayTasks.taskId,
                period.time,
                index
            )
        }
    }

    override fun tasks(): Flow<List<Task>> = flow { emit(localDataSource.tasks()) }

    override fun taskPeriodsById(id: Int): Flow<TaskWithPeriods> =
        flow { emit(localDataSource.taskPeriodsById(id)) }

    override fun account(): Flow<Account> = flow { emit(localDataSource.account()) }
}


