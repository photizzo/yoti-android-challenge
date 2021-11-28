package com.yoti.android.cryptocurrencychallenge.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedAssetData
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedMarketData
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData

@Dao
interface CoinDao {
    @Query("SELECT * FROM cachedassetdata")
    suspend fun getAllAssets(): List<AssetData>

    @Query("SELECT * FROM cachedmarketdata")
    suspend fun getAllMarketData(): List<MarketData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAssets(assets: List<CachedAssetData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMarketData(marketData: List<CachedMarketData>)
}