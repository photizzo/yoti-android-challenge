package com.yoti.android.cryptocurrencychallenge.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentMarketBinding
import com.yoti.android.cryptocurrencychallenge.presentation.util.formatDisplayDate
import com.yoti.android.cryptocurrencychallenge.presentation.util.handleNullableString
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : Fragment() {
    private val coinViewModel by activityViewModels<CoinViewModel>()
    private lateinit var binding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = MarketFragmentArgs.fromBundle(arguments ?: return)
        val assetData = coinViewModel.getAssetBySymbol(args.id.toString())
        val marketData =
            coinViewModel.getMarketDataForAsset(assetData?.id.handleNullableString())
        if (marketData == null || assetData == null) return
        binding.setupUI(marketData, assetData)
    }

    private fun FragmentMarketBinding.setupUI(marketData: MarketData, assetData: AssetData) {
        textViewExchangeId.text = marketData.exchangeId
        textViewRank.text = marketData.rank
        textViewPrice.text = assetData.priceUsd
        textViewDate.text = marketData.updated?.formatDisplayDate()
    }

}