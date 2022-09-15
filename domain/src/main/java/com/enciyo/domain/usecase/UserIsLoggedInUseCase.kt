package com.enciyo.domain.usecase

import com.enciyo.domain.Repository
import com.enciyo.domain.UseCase
import com.enciyo.shared.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserIsLoggedInUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val repository: Repository,
) : UseCase<Unit, Boolean>(ioDispatcher) {
    override fun execute(input: Unit): Flow<Boolean> =
        repository.isLoggedIn()
}