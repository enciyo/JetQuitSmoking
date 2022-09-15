package com.enciyo.data.repo

import com.enciyo.data.entity.TaskEntity
import com.enciyo.shared.IoDispatcher
import com.enciyo.shared.currentSystemTimeZone
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskCreator @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    private val clock = Clock.System.now()
        .minus(4, DateTimeUnit.DAY, currentSystemTimeZone)

    private val timeZone = currentSystemTimeZone

    suspend operator fun invoke(smokedPerDay: Int): List<TaskEntity> = withContext(ioDispatcher) {
        var mod = smokedPerDay.mod(RepositoryImp.TASK_COUNT)
        val subtract = (smokedPerDay / RepositoryImp.TASK_COUNT)
        var wrappedSmoked = smokedPerDay
        return@withContext (RepositoryImp.TASK_COUNT downTo 1)
            .map {
                val extraForMod = (if (mod > 0) 1 else 0).also { mod -= 1 }
                val id = (RepositoryImp.TASK_COUNT + 1) - it
                val date = clock.plus(id, DateTimeUnit.DAY, timeZone = timeZone)
                    .toLocalDateTime(timeZone)
                wrappedSmoked = wrappedSmoked - subtract - extraForMod
                TaskEntity(id, wrappedSmoked, date)
            }
    }
}