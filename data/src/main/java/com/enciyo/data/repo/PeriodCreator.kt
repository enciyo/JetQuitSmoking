package com.enciyo.data.repo

import com.enciyo.data.entity.Period
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeriodCreator @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private val timeZone = TimeZone.currentSystemDefault()
    private val clock = Clock.System.now().minus(1, DateTimeUnit.DAY, timeZone)

    suspend operator fun invoke(task: Task): TaskPeriods = withContext(ioDispatcher) {
        val newDate = clock.plus(task.taskId, DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone)
        val perMinute = RepositoryImp.DAILY_MINUTES / task.needSmokeCount
        var startedDate = LocalDateTime(
            hour = 8,
            minute = 1,
            second = 0,
            year = newDate.year,
            month = newDate.month,
            dayOfMonth = newDate.dayOfMonth
        )
        val taskPeriods = (1..task.needSmokeCount)
            .map {
                Period(id = it, time = startedDate.toString()).also {
                    startedDate = startedDate.addMinute(perMinute)
                }
            }
            .toList()
            .let { TaskPeriods(taskId = task.taskId, period = it) }
        return@withContext taskPeriods
    }

    private fun LocalDateTime.addMinute(minute: Int): LocalDateTime {
        val onCurrentMinute = (minute + this.minute)
        val mod = onCurrentMinute % 60
        val extraHour = onCurrentMinute / 60
        return LocalDateTime(
            hour = this.hour + extraHour,
            minute = mod,
            year = this.year,
            dayOfMonth = this.dayOfMonth,
            month = this.month
        )
    }
}