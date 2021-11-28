package com.yoti.android.cryptocurrencychallenge.data

import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCache
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRemote
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleError
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CoinRepoImpl @Inject constructor(
    private val coinCache: CoinCache,
    private val coinRemote: CoinRemote
): CoinRepository {

    override fun getAssets(): Flow<List<AssetData>> {
        val remote = coinRemote.getAssets().map {result ->
            coinCache.saveAssets(result)
            result
        }
        val cache = coinCache.getAssets()
        return cache.flatMapConcat {result ->
            if(result.isEmpty()) remote
            else flowOf(result)
        }.onCompletion { remote }
    }

    override fun getMarkets(): Flow<List<MarketData>> {
        val remote = coinRemote.getMarkets().map {result ->
            coinCache.saveMarketData(result)
            result
        }
        val cache = coinCache.getMarkets().handleError {}
        return cache.flatMapConcat {result ->
            if(result.isEmpty()) remote
            else flowOf(result)
        }.onCompletion { remote }
    }
}