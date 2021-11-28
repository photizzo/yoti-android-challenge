package com.yoti.android.cryptocurrencychallenge.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedMarketData(
    @PrimaryKey
    val baseId: String,
    @ColumnInfo(name = "baseSymbol")
    val baseSymbol: String?,
    @ColumnInfo(name = "exchangeId")
    val exchangeId: String?,
    @ColumnInfo(name = "percentExchangeVolume")
    val percentExchangeVolume: String?,
    @ColumnInfo(name = "priceQuote")
    val priceQuote: String?,
    @ColumnInfo(name = "priceUsd")
    val priceUsd: String?,
    @ColumnInfo(name = "quoteId")
    val quoteId: String?,
    @ColumnInfo(name = "quoteSymbol")
    val quoteSymbol: String?,
    @ColumnInfo(name = "rank")
    val rank: String?,
    @ColumnInfo(name = "tradesCount24Hr")
    val tradesCount24Hr: String?,
    @ColumnInfo(name = "updated")
    val updated: Long?,
    @ColumnInfo(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String?
)