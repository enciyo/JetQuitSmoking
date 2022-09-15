package com.enciyo.domain.usecase

import com.enciyo.domain.UseCase
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserSmokedPerDayUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val getAccountUseCase: GetAccountUseCase,
) : UseCase<Unit, Int>(ioDispatcher) {
    override fun execute(input: Unit): Flow<Int> =
        getAccountUseCase.invoke(input).map { it.cigarettesSmokedPerDay }
}