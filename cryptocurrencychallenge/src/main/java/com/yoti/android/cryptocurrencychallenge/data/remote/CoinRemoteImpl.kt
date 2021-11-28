package com.yoti.android.cryptocurrencychallenge.data.remote

import com.yoti.android.cryptocurrencychallenge.data.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRemoteImpl @Inject constructor(
    private val api: CoincapService
): CoinRemote {

    override fun getAssets(): Flow<List<AssetData>> {
        return flow { emit(api.getAssets()) }
            .map { result ->
                result.assetData ?: listOf()
            }
    }

    override fun getMarkets(): Flow<List<MarketData>> {
        return flow { emit(api.getMarkets()) }
            .map { result ->
                result.marketData ?: listOf()
            }
    }
}