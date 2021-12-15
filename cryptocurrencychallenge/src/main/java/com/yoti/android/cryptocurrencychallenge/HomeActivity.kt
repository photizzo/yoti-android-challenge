package com.yoti.android.cryptocurrencychallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yoti.android.cryptocurrencychallenge.databinding.HomeActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewBinding: HomeActivityBinding by lazy {
        HomeActivityBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}