package com.yoti.android.cryptocurrencychallenge.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentAssetsBinding
import com.yoti.android.cryptocurrencychallenge.presentation.state.UIState
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.CoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssetsFragment : Fragment() {

    private lateinit var binding: FragmentAssetsBinding
    private val coinViewModel by activityViewModels<CoinViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRetry.setOnClickListener {
            coinViewModel.getAssets()
        }
        coinViewModel.getAssetsLiveData.observe(viewLifecycleOwner, { displayAssets(it) })
    }

    /**
     * Listen for clicks on asset item
     */
    private fun onItemClickListener(assetUiItem: AssetUiItem) {
        val action = AssetsFragmentDirections.actionAssetsFragmentToMarketFragment(assetUiItem.symbol)
        findNavController().navigate(action)
    }

    /**
     * Handle state for how assets are displayed
     * @param result  State of the list result [AssetUiItem]
     */
    private fun displayAssets(result: UIState<List<AssetUiItem>>) {
        when (result) {
            is UIState.Loading -> {
                binding.progressBarAssets.visibility = View.VISIBLE
                binding.recyclerViewAssets.visibility = View.GONE
                binding.errorLayout.visibility = View.GONE
            }
            is UIState.Success -> {
                binding.progressBarAssets.visibility = View.GONE
                if(result.body.isNotEmpty()) {
                    binding.recyclerViewAssets.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                    binding.recyclerViewAssets.adapter =
                        AssetsAdapter(result.body) { onItemClickListener(it) }
                } else {
                    binding.recyclerViewAssets.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.tvError.text = getString(R.string.empty_data)
                }

            }
            is UIState.Failed -> {
                binding.progressBarAssets.visibility = View.GONE
                binding.recyclerViewAssets.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
                binding.tvError.text = result.error
            }
        }
    }
}