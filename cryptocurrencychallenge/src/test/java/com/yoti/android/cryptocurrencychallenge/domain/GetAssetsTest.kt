package com.yoti.android.cryptocurrencychallenge.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yoti.android.cryptocurrencychallenge.domain.repository.CoinRepository
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetAssets
import com.yoti.android.cryptocurrencychallenge.utils.generateAssetData
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
class GetAssetsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var getAssets: GetAssets
    val dispatcher = Dispatchers.Unconfined
    @MockK
    lateinit var coinRepository: CoinRepository

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAssets = GetAssets(coinRepository)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `get posts executes`() = runBlocking{
        coEvery { coinRepository.getAssets() } returns flowOf(generateAssetData(10))
        val result = getAssets.execute().first()
        assert(result.first().id.toString().isNullOrEmpty().not())
        assert(result.first().name.toString().isNullOrEmpty().not())
        assert(result.first().symbol.isNullOrEmpty().not())
    }
}