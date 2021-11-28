package com.yoti.android.cryptocurrencychallenge.domain.usecase

import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting list of market data
 */
class GetMarketData @Inject constructor(
    private val repository: CoinRepository,
) : FlowUseCase<List<MarketData>, Unit>() {
    override suspend fun buildFlowUseCase(params: Unit?): Flow<List<MarketData>> {
        return repository.getMarkets()
    }
}