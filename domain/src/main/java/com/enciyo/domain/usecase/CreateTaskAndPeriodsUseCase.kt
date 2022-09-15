package com.enciyo.domain.usecase

import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CreateTaskAndPeriodsUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val createTaskUseCase: CreateTaskUseCase,
    private val createPeriodUseCase: CreatePeriodUseCase,
) : UseCase<Int, List<TaskWithPeriods>>(ioDispatcher) {
    override fun execute(input: Int): Flow<List<TaskWithPeriods>> =
        createTaskUseCase.invoke(input)
            .map { tasks ->
                tasks.map { task ->
                    TaskWithPeriods(
                        task = task,
                        periods = createPeriodUseCase.invoke(task).first()
                    )
                }
            }


}