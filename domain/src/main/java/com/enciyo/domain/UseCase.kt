package com.enciyo.domain

import com.enciyo.shared.IoDispatcher
import com.enciyo.shared.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


abstract class UseCase<I, O>(private val ioDispatcher: CoroutineDispatcher) {

    @Inject
    @IoDispatcher
    lateinit var i2oDispatcher: CoroutineDispatcher

    operator fun invoke(input: I): Flow<O> =
        execute(input)
            .flowOn(ioDispatcher)
            .catch { t ->
                val className = this::class.simpleName
                log("$className -> ${t.message}")
            }

    protected abstract fun execute(input: I): Flow<O>
}


