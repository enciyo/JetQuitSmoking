package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetTaskDetailByIdUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
) : UseCase<Int, TaskWithPeriods>(ioDispatcher) {
    override fun execute(input: Int): Flow<TaskWithPeriods> =
        repository.taskPeriodsById(input)

}