package com.enciyo.data.repo

import com.enciyo.data.SessionAlarmManager
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Period
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskWithPeriods
import com.enciyo.data.source.LocalDataSource
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val sessionAlarmManager: SessionAlarmManager,
    private val taskCreator: TaskCreator,
    private val periodCreator: PeriodCreator,
) : Repository {

    companion object {
        const val TASK_COUNT = 21
        const val DAILY_MINUTES = 900
    }

    override suspend fun singUp(account: Account) {
        localDataSource.save(account)
        val tasks = createTasks()
        tasks.forEach { task ->
            val taskPeriod = createTaskPeriod(task)
            if (task.taskId == 0 && taskPeriod.isNotEmpty()) {
                val period = taskPeriod.first()
                sessionAlarmManager(task.taskId, period.time, task.needSmokeCount)
            }
        }
    }

    override suspend fun isLoggedIn(): Boolean =
        localDataSource.isLoggedIn()


    private suspend fun createTasks(): List<Task> {
        val account = localDataSource.account()
        val smokedPerDay = account.cigarettesSmokedPerDay
        val tasks = taskCreator(smokedPerDay)
        localDataSource.saveAll(*tasks.toTypedArray())
        return tasks
    }


    private suspend fun createTaskPeriod(task: Task): List<Period> {
        if (task.needSmokeCount <= 0) return listOf()
        val taskPeriod = periodCreator(task)
        localDataSource.saveAll(*taskPeriod.toTypedArray())
        return taskPeriod
    }


    override suspend fun setNextAlarm() {
        //TODO("Create alarm for next sesion")
    }

    override suspend fun tasks(): List<Task> = localDataSource.tasks()

    override suspend fun taskPeriodsById(id: Int): TaskWithPeriods =
        localDataSource.taskPeriodsById(id)

    override suspend fun account(): Account = localDataSource.account()

    private suspend fun <T> onIoThread(block: suspend () -> T) =
        withContext(ioDispatcher) { block.invoke() }

}


