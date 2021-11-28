package com.yoti.android.cryptocurrencychallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yoti.android.cryptocurrencychallenge.databinding.HomeActivityBinding
import com.yoti.android.cryptocurrencychallenge.market.MarketFragment
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewBinding: HomeActivityBinding by lazy {
        HomeActivityBinding.inflate(
            layoutInflater
        )
    }

    private val coinViewModel by viewModels<CoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)
    }
}