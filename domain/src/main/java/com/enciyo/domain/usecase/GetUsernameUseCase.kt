package com.enciyo.domain.usecase

import com.enciyo.domain.UseCase
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsernameUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val getAccountUseCase: GetAccountUseCase,
) : UseCase<Unit, String>(ioDispatcher) {
    override fun execute(input: Unit): Flow<String> =
        getAccountUseCase.invoke(input).map { it.name }
}