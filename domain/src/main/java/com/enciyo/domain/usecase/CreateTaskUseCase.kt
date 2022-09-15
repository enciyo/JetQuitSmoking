package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.Task
import com.enciyo.shared.IoDispatcher
import com.enciyo.shared.currentSystemTimeZone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CreateTaskUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
) : UseCase<Int, List<Task>>(ioDispatcher) {

    private val now = Clock.System.now()
        .minus(4, DateTimeUnit.DAY, currentSystemTimeZone)
    private val timeZone = currentSystemTimeZone

    override fun execute(input: Int): Flow<List<Task>> = flow {
        emit(createTasks(input))
    }
        .flatMapConcat(repository::saveTasks)

    private suspend fun createTasks(smokedPerDay: Int): List<Task> =
        withContext(ioDispatcher) {
            var mod = smokedPerDay.mod(TASK_COUNT)
            val div = smokedPerDay.div(TASK_COUNT)
            var mutableSmokedPerDay = smokedPerDay
            var date = now
            return@withContext (TASK_COUNT downTo 1)
                .map {
                    val extras = (if (mod > 0) 1 else 0).also { mod -= 1 }
                    val id = (TASK_COUNT + 1) - it
                    date = date.plusDay(1)
                    mutableSmokedPerDay = mutableSmokedPerDay - div - extras
                    Task(id, mutableSmokedPerDay, date.toLocalDateTime(timeZone))
                }
        }

    companion object {
        private const val TASK_COUNT = 21
    }

    private fun Instant.plusDay(value: Int) =
        this.plus(value, DateTimeUnit.DAY, currentSystemTimeZone)
}

