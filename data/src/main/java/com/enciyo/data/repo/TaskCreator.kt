package com.enciyo.data.repo

import com.enciyo.data.entity.Task
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskCreator @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(smokedPerDay: Int): List<Task> = withContext(ioDispatcher) {
        var mod = smokedPerDay.mod(RepositoryImp.TASK_COUNT)
        val subtract = (smokedPerDay / RepositoryImp.TASK_COUNT)
        var wrappedSmoked = smokedPerDay
        return@withContext (RepositoryImp.TASK_COUNT downTo 1)
            .map {
                val extraForMod = (if (mod > 0) 1 else 0).also { mod -= 1 }
                val id = (RepositoryImp.TASK_COUNT + 1) - it
                wrappedSmoked = wrappedSmoked - subtract - extraForMod
                Task(id, wrappedSmoked)
            }
    }
}