package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.domain.dto.Account
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
) : UseCase<Unit, Account>(ioDispatcher) {
    override fun execute(input: Unit): Flow<Account> =
        repository.account()
}


