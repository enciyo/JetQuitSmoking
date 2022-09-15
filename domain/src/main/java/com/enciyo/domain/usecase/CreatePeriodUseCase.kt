package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.Period
import com.enciyo.domain.dto.Task
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CreatePeriodUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
) : UseCase<Task, List<Period>>(ioDispatcher) {
    override fun execute(input: Task): Flow<List<Period>> = flow {
        emit(createPeriods(
            startedDate = input.taskTime,
            needSmokeCount = input.needSmokeCount,
            taskId = input.taskId
        ))
    }
        .flatMapConcat(repository::savePeriods)

    private suspend fun createPeriods(
        startedDate: LocalDateTime,
        needSmokeCount: Int,
        taskId: Int,
    ): List<Period> = withContext(ioDispatcher) {
        val perMinute = 900 / needSmokeCount
        var startDate = LocalDateTime(
            hour = 8,
            minute = 1,
            second = 0,
            year = startedDate.year,
            month = startedDate.month,
            dayOfMonth = startedDate.dayOfMonth
        )
        val taskPeriods = (1..needSmokeCount).map { _ ->
            Period(time = startDate, taskId = taskId).also {
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

