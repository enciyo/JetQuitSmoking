package com.enciyo.data.repo

import android.util.Log
import com.enciyo.data.entity.Account
import com.enciyo.data.entity.Period
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods
import com.enciyo.data.source.LocalDataSource
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalTime
import javax.inject.Inject
import kotlin.math.min

class RepositoryImp @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource
) : Repository {

    companion object {
        // TODO: Move to build.gradle.kts
        const val TASK_COUNT = 21
        const val DAILY_MINUTES = 900
    }

    override suspend fun singUp(account: Account) {
        localDataSource.save(account)
        createTasks()
    }

    override suspend fun isLoggedIn(): Boolean =
        localDataSource.isLoggedIn()

    private suspend fun createTasks() {
        val account = localDataSource.account()
        val smokedPerDay = account.cigarettesSmokedPerDay
        var mod = smokedPerDay.mod(TASK_COUNT)
        val subtracted = (smokedPerDay / TASK_COUNT)
        var wrappedSmoked = smokedPerDay
        val tasks = onIoThread {
            (TASK_COUNT downTo 1)
                .map {
                    val extraForMod = (if (mod > 0) 1 else 0).also { mod -= 1 }
                    wrappedSmoked = wrappedSmoked - subtracted - extraForMod
                    val id = (TASK_COUNT + 1) - it
                    Task(id, wrappedSmoked)
                        .also { task ->
                            createTaskPeriod(task)
                        }
                }
                .toList()
        }
        localDataSource.saveAll(*tasks.toTypedArray())
    }

    private suspend fun createTaskPeriod(task: Task) {
        if (task.needSmokeCount <= 0) return
        val perMinute = DAILY_MINUTES / task.needSmokeCount
        var startedDate = LocalTime(hour = 8, minute = 1, second = 0)
        val taskPeriods = onIoThread {
            (1..task.needSmokeCount)
                .map {
                    Period(id = it, time = startedDate.toString()).also {
                        startedDate = startedDate.addMinute(perMinute)
                    }
                }
                .toList()
                .let { TaskPeriods(taskId = task.taskId, period = it) }
        }
        localDataSource.saveAll(taskPeriods)
    }


    override suspend fun tasks(): List<Task> = localDataSource.tasks()

    override suspend fun taskPeriodsById(id: Int): TaskPeriods = localDataSource.taskPeriodsById(id)

    override suspend fun account(): Account = localDataSource.account()

    private suspend fun <T> onIoThread(block: suspend () -> T) =
        withContext(ioDispatcher) { block.invoke() }

}


fun LocalTime.addMinute(minute: Int): LocalTime {
    val onCurrentMinute = (minute + this.minute)
    val mod = onCurrentMinute % 60
    val wrappedLocalTime = this
    val extraHour = onCurrentMinute / 60
    return LocalTime(hour = wrappedLocalTime.hour + extraHour, minute = mod)
}
