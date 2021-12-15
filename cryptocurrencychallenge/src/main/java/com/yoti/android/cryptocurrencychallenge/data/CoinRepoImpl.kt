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
        //1. Fetch assets from api
        val remote = coinRemote.getAssets().map {result ->
            //2. Save assets to device cache
            coinCache.saveAssets(result)
            result
        }
        // 3. Fetch assets for device cache
        val cache = coinCache.getAssets()

        //4 Fetch and return assets from cache if its not empty
        //5 Return assets from api if data is not cached
        return cache.flatMapConcat {result ->
            if(result.isEmpty()) remote
            else flowOf(result)
        }
    }

    override fun getMarkets(): Flow<List<MarketData>> {
        //1. Fetch market data from api
        val remote = coinRemote.getMarkets().map {result ->
            //2. Save market data to device cache
            coinCache.saveMarketData(result)
            result
        }
        // 3. Fetch market data for device cache
        val cache = coinCache.getMarkets().handleError {}
        //4 Fetch and return market data from cache if its not empty
        //5 Return market data from api if data is not cached
        return cache.flatMapConcat {result ->
            if(result.isEmpty()) remote
            else flowOf(result)
        }
    }
}