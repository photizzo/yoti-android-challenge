package com.yoti.android.cryptocurrencychallenge.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoti.android.cryptocurrencychallenge.data.cache.dao.CoinDao
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedAssetData
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedMarketData

@Database(entities = [CachedAssetData::class, CachedMarketData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}