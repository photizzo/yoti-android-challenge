package com.yoti.android.cryptocurrencychallenge.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetMarketData
import com.yoti.android.cryptocurrencychallenge.utils.generateAssetData
import com.yoti.android.cryptocurrencychallenge.utils.generateMarketData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetMarketDataTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var getMarketData: GetMarketData
    val dispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var coinRepository: CoinRepository

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getMarketData = GetMarketData(coinRepository)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `get posts executes`() = runBlocking {
        coEvery { coinRepository.getMarkets() } returns flowOf(generateMarketData(10))
        val result = getMarketData.execute().first()
        assert(result.first().baseId.toString().isNullOrEmpty().not())
        assert(result.first().priceUsd.toString().isNullOrEmpty().not())
        assert(result.first().volumeUsd24Hr.isNullOrEmpty().not())
        assert(result.first().updated.toString().isNullOrEmpty().not())
    }
}