package com.yoti.android.cryptocurrencychallenge.data

import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetsApiData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketsApiData
import retrofit2.http.GET

const val CAPCOIN_ENDPOINT_HOST = "https://de2dc5b2-d905-4b93-aaeb-8122bdf32c26.mock.pstmn.io"

interface CoincapService {

    @GET("/v2/assets")
    suspend fun getAssets(): AssetsApiData

    @GET("/v2/markets")
    suspend fun getMarkets(): MarketsApiData
}