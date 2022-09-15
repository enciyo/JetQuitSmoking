package com.enciyo.data.repo

import com.enciyo.data.entity.PeriodEntity
import com.enciyo.data.entity.TaskEntity
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

    suspend operator fun invoke(taskEntity: TaskEntity): List<PeriodEntity> = withContext(ioDispatcher) {
        val newDate = taskEntity.time
        val perMinute = RepositoryImp.DAILY_MINUTES / taskEntity.needSmokeCount
        var startDate = LocalDateTime(
            hour = 8,
            minute = 1,
            second = 0,
            year = newDate.year,
            month = newDate.month,
            dayOfMonth = newDate.dayOfMonth
        )


        val taskPeriodEntities = (1..taskEntity.needSmokeCount)
            .map { id ->
                PeriodEntity(time = startDate, taskCreatorId = taskEntity.taskId).also {
                    startDate.addMinute(perMinute).let { startDate = it }
                }
            }
            .toList()
        return@withContext taskPeriodEntities
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