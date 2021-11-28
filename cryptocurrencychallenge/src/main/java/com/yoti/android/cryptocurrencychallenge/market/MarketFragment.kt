package com.yoti.android.cryptocurrencychallenge.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.data.model.assets.AssetData
import com.yoti.android.cryptocurrencychallenge.data.model.markets.MarketData
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentAssetsBinding
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
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getString(KEY_SYMBOL)
        args?.apply {
            val assetData = coinViewModel.getAssetBySymbol(this)
            val marketData =
                coinViewModel.getMarketDataForAsset(assetData?.id.handleNullableString())
            if (marketData == null || assetData == null) return@apply
            binding.setupUI(marketData, assetData)
        }
    }

    private fun FragmentMarketBinding.setupUI(marketData: MarketData, assetData: AssetData) {
        textViewExchangeId.text = marketData.exchangeId
        textViewRank.text = marketData.rank
        textViewPrice.text = assetData.priceUsd
        textViewDate.text = marketData.updated?.formatDisplayDate()
    }

    companion object {
        private const val TAG = "MarketFragment"
        private const val KEY_SYMBOL = "id"

        /**
         * Helper method to manage display of Market Fragment
         * @param id ID for the coin that needs to be displayed
         * @param fragmentManager Fragment Manager
         */
        fun displayMarketFragment(symbol: String, fragmentManager: FragmentManager) {
            val fragment = MarketFragment()
            val args = Bundle()
            args.putString(KEY_SYMBOL, symbol)
            fragment.arguments = args
            fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerHome, fragment)
                .addToBackStack(TAG)
                .commit()
        }
    }
}