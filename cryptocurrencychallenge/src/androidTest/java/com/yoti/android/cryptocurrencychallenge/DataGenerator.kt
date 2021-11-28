package com.yoti.android.cryptocurrencychallenge

import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedAssetData
import com.yoti.android.cryptocurrencychallenge.data.cache.entities.CachedMarketData
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import kotlin.random.Random

private fun assetData(): AssetData = AssetData(
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
)
private fun marketData(): MarketData = MarketData(
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomLong(),
    randomString(),
)

fun generateAssetData(count: Int): List<AssetData> = (1..count).map {
    assetData()
}
fun generateMarketData(count: Int): List<MarketData> = (1..count).map {
    marketData()
}

private fun assetCachedData(): CachedAssetData = CachedAssetData(
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
)
private fun marketCachedData(): CachedMarketData = CachedMarketData(
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomString(),
    randomLong(),
    randomString(),
)

fun generateCacheAssetData(count: Int): List<CachedAssetData> = (1..count).map {
    assetCachedData()
}
fun generateCacheMarketData(count: Int): List<CachedMarketData> = (1..count).map {
    marketCachedData()
}


fun randomLong() = (0..1000000000000).random()

fun randomString(size: Int = 20): String = (0..size)
    .map { charPool[Random.nextInt(0, charPool.size)] }
    .joinToString()

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
