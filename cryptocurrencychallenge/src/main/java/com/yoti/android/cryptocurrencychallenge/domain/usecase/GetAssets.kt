package com.yoti.android.cryptocurrencychallenge.domain.usecase

import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting list of assets
 */
class GetAssets @Inject constructor(
    private val repository: CoinRepository,
) : FlowUseCase<List<AssetData>, Unit>() {
    override suspend fun buildFlowUseCase(params: Unit?): Flow<List<AssetData>> {
        return repository.getAssets()
    }
}