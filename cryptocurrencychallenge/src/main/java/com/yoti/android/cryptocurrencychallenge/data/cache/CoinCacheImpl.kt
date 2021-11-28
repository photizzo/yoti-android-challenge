package com.yoti.android.cryptocurrencychallenge.data.cache

import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedAssetData
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedMarketData
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinCache
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleNullableString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class CoinCacheImpl @Inject constructor(
    private val db: AppDatabase
) : CoinCache {

    override fun getAssets(): Flow<List<AssetData>> {
        return flow { emit(db.coinDao().getAllAssets()) }
            .map { list ->
                list.map { data ->
                    AssetData(
                        data.changePercent24Hr,
                        data.explorer,
                        data.id,
                        data.marketCapUsd,
                        data.maxSupply,
                        data.name,
                        data.priceUsd,
                        data.rank,
                        data.supply,
                        data.symbol,
                        data.volumeUsd24Hr,
                        data.vwap24Hr,
                    )
                }
            }
    }

    override fun getMarkets(): Flow<List<MarketData>> {
        return flow { emit(db.coinDao().getAllMarketData()) }
            .map { list ->
                list.map { data ->
                    MarketData(
                        data.baseId,
                        data.baseSymbol,
                        data.exchangeId,
                        data.percentExchangeVolume,
                        data.priceQuote,
                        data.priceUsd,
                        data.quoteId,
                        data.quoteSymbol,
                        data.rank,
                        data.tradesCount24Hr,
                        data.updated,
                        data.volumeUsd24Hr,
                    )
                }
            }
    }

    override suspend fun saveAssets(assets: List<AssetData>) {
        val result = assets.map { data ->
            CachedAssetData(
                data.changePercent24Hr,
                data.explorer,
                data.id.handleNullableString(),
                data.marketCapUsd,
                data.maxSupply,
                data.name,
                data.priceUsd,
                data.rank,
                data.supply,
                data.symbol,
                data.volumeUsd24Hr,
                data.vwap24Hr,
            )
        }
        db.coinDao().insertAllAssets(result)
    }

    override suspend fun saveMarketData(marketData: List<MarketData>) {
        val result = marketData.map { data ->
            CachedMarketData(
                data.baseId.handleNullableString(),
                data.baseSymbol,
                data.exchangeId,
                data.percentExchangeVolume.toString(),
                data.priceQuote,
                data.priceUsd,
                data.quoteId,
                data.quoteSymbol,
                data.rank,
                data.tradesCount24Hr.toString(),
                data.updated,
                data.volumeUsd24Hr,
            )
        }
        db.coinDao().insertAllMarketData(result)
    }
}