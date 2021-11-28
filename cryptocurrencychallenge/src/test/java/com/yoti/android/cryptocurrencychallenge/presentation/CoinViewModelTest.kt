package com.yoti.android.cryptocurrencychallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetAssets
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetMarketData
import com.yoti.android.cryptocurrencychallenge.presentation.state.UIState
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleNullableString
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.CoinViewModel
import com.yoti.android.cryptocurrencychallenge.utils.generateAssetData
import com.yoti.android.cryptocurrencychallenge.utils.generateMarketData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoinViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var getAssets: GetAssets
    @MockK
    lateinit var getMarketData: GetMarketData
    lateinit var coinViewModel: CoinViewModel
    val dispatcher = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coinViewModel = spyk(CoinViewModel(getAssets, getMarketData, dispatcher))
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `get assets and market data is successful`() {
        coEvery { getAssets.execute(any()) } returns flowOf(generateAssetData(10))
        coEvery { getMarketData.execute() } returns flowOf(generateMarketData(10))
        coinViewModel.getAssets()
        coinViewModel.getAssetsLiveData.observeForever{}

        assert(coinViewModel.getAssetsLiveData.value != null)
        assert(coinViewModel.getAssetsLiveData.value is UIState.Success)
    }

    @Test
    fun `get assets when fetch assets fails`() {
        coEvery { getAssets.execute(any()) } coAnswers   { throw Exception("No result") }
        coEvery { getMarketData.execute() } returns flowOf(generateMarketData(10))
        coinViewModel.getAssets()
        coinViewModel.getAssetsLiveData.observeForever{}

        assert(coinViewModel.getAssetsLiveData.value != null)
        assert(coinViewModel.getAssetsLiveData.value is UIState.Failed)
    }

    @Test
    fun `get assets fails when fetch market fails`() {
        coEvery { getAssets.execute(any()) } returns flowOf(generateAssetData(10))
        coEvery { getMarketData.execute() } coAnswers   { throw Exception("No result") }
        coinViewModel.getAssets()
        coinViewModel.getAssetsLiveData.observeForever{}

        assert(coinViewModel.getAssetsLiveData.value != null)
        assert(coinViewModel.getAssetsLiveData.value is UIState.Failed)
    }

    @Test
    fun `get assets by symbol succeeds`() {
        val assetData = generateAssetData(10)
        every { coinViewModel.assetData } returns assetData

        val result = coinViewModel.getAssetBySymbol(assetData.first().symbol.handleNullableString())

        assert(result?.id.toString().isNullOrEmpty().not())
        assert(result?.name.toString().isNullOrEmpty().not())
        assert(result?.symbol.toString().isNullOrEmpty().not())
    }

    @Test
    fun `get market data by assets id succeeds`() {
        val assetData = generateAssetData(10)
        val firstAsset = assetData.first()
        val marketData = generateMarketData(1)
        val marketDataModified = marketData.first().copy(baseId = firstAsset.id)
        val marketDataResult = mutableListOf(marketDataModified)
        marketDataResult.addAll(marketDataResult)
        every { coinViewModel.marketData } returns marketDataResult
        every { coinViewModel.assetData } returns assetData

        val result = coinViewModel.getMarketDataForAsset(firstAsset.id.handleNullableString())

        assert(result?.baseId.toString().isEmpty().not())
        assert(result?.priceUsd.toString().isEmpty().not())
        assert(result?.volumeUsd24Hr.toString().isEmpty().not())
        assert(result?.updated.toString().isEmpty().not())
    }



}