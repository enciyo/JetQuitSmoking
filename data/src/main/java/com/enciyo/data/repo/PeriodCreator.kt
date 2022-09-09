package com.enciyo.data.repo

import com.enciyo.data.entity.Period
import com.enciyo.data.entity.Task
import com.enciyo.data.entity.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import com.enciyo.shared.currentSystemTimeZone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeriodCreator @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    private val clock = Clock.System.now().minus(1, DateTimeUnit.DAY, currentSystemTimeZone)

    suspend operator fun invoke(task: Task): List<Period> = withContext(ioDispatcher) {
        val newDate = clock.plus(task.taskId, DateTimeUnit.DAY, currentSystemTimeZone).toLocalDateTime(
            currentSystemTimeZone)
        val perMinute = RepositoryImp.DAILY_MINUTES / task.needSmokeCount
        var startDate = LocalDateTime(
            hour = 8,
            minute = 1,
            second = 0,
            year = newDate.year,
            month = newDate.month,
            dayOfMonth = newDate.dayOfMonth
        )


        val taskPeriods = (1..task.needSmokeCount)
            .map { id ->
                Period(time = startDate, taskCreatorId = task.taskId).also {
                    startDate.addMinute(perMinute).let { startDate = it }
                }
            }
            .toList()
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