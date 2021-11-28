package com.yoti.android.cryptocurrencychallenge.domain.repository

import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface CoinCache {
    /**
     * Get Assests
     */
    fun getAssets(): Flow<List<AssetData>>

    /**
     * Get Markets
     */
    fun getMarkets(): Flow<List<MarketData>>

    /**
     * Save Assests
     */
    suspend  fun saveAssets(assets: List<AssetData>)

    /**
     * Get Markets
     */
    suspend fun saveMarketData(marketData: List<MarketData>)

}