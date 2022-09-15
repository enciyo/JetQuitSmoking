package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.Account
import com.enciyo.domain.dto.TaskWithPeriods
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SaveAccountUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
    private val createTaskAndPeriodsUseCase: CreateTaskAndPeriodsUseCase,
) : UseCase<Account, List<TaskWithPeriods>>(ioDispatcher) {
    override fun execute(input: Account): Flow<List<TaskWithPeriods>> =
        repository.saveAccount(input)
            .map { it.cigarettesSmokedPerDay }
            .flatMapConcat(createTaskAndPeriodsUseCase::invoke)
}