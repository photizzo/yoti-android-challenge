package com.yoti.android.cryptocurrencychallenge.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedAssetData(
    @ColumnInfo(name = "changePercent24Hr")
    val changePercent24Hr: String?,
    @ColumnInfo(name = "explorer")
    val explorer: String?,
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "marketCapUsd")
    val marketCapUsd: String?,
    @ColumnInfo(name = "maxSupply")
    val maxSupply: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "priceUsd")
    val priceUsd: String?,
    @ColumnInfo(name = "rank")
    val rank: String?,
    @ColumnInfo(name = "supply")
    val supply: String?,
    @ColumnInfo(name = "symbol")
    val symbol: String?,
    @ColumnInfo(name = "volumeUsd24Hr")
    val volumeUsd24Hr: String?,
    @ColumnInfo(name = "vwap24Hr")
    val vwap24Hr: String?
)