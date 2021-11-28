package com.yoti.android.cryptocurrencychallenge.domain.usecase

import kotlinx.coroutines.flow.Flow


/**
 * Abstract class for a Flow UseCase that returns an instance of a [Flow].
 */
abstract class FlowUseCase<T, in Params> {

    protected abstract suspend fun buildFlowUseCase(params: Params? = null): Flow<T>

    open suspend fun execute(params: Params? = null): Flow<T> {
        return this.buildFlowUseCase(params)
    }
}