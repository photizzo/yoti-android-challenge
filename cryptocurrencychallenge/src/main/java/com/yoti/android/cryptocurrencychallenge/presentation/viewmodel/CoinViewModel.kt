package com.yoti.android.cryptocurrencychallenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.assets.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetAssets
import com.yoti.android.cryptocurrencychallenge.domain.usecase.GetMarketData
import com.yoti.android.cryptocurrencychallenge.presentation.state.UIState
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleError
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleNullableString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [CoinViewModel] handle all interactions with the UI layer
 */
@HiltViewModel
class CoinViewModel @Inject constructor(
    private val getAssets: GetAssets,
    private val getMarketData: GetMarketData,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO //allow for dispatchers to be injected during testing
) : ViewModel() {
    private val _getAssetsLiveData: MutableLiveData<UIState<List<AssetUiItem>>> = MutableLiveData()
    val getAssetsLiveData: LiveData<UIState<List<AssetUiItem>>>
        get() = _getAssetsLiveData

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _getAssetsLiveData.postValue(UIState.Failed(exception.localizedMessage))
    }

    var marketData: List<MarketData> = listOf()
    var assetData: List<AssetData> = listOf()

    init {
        getAssets()
    }

    /**
     * Returns the market data for a particular asset
     * @param id ID for the asset
     */
    fun getMarketDataForAsset(id: String): MarketData? {
        return marketData.firstOrNull {
            it.baseId == id
        }
    }
    /**
     * Returns the asset data for a particular asset
     * @param id ID for the asset
     */
    fun getAssetBySymbol(symbol: String): AssetData?{
        return assetData.firstOrNull {
            it.symbol == symbol
        }
    }

    /**
     * Get Assets
     */
    fun getAssets() {
        _getAssetsLiveData.postValue(UIState.Loading)
        viewModelScope.launch(errorHandler + dispatcher) {
            getAssets.execute()
                .flatMapConcat {result ->
                    assetData = result
                    getMarketData.execute()
                }
                .onEach { data ->
                    marketData = data
                    val assets = assetData.map {
                        AssetUiItem(
                            it.symbol.handleNullableString(),
                            it.name.handleNullableString(),
                            it.priceUsd.handleNullableString(),
                        )
                    }
                    _getAssetsLiveData.postValue(UIState.Success(assets))
                }.handleError { error ->
                    // error response
                    _getAssetsLiveData.postValue(UIState.Failed(error))
                }.collect()
        }
    }

}