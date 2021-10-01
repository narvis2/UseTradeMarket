package com.example.usetrademarket.presentation.view.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.FragmentHomeBinding
import com.example.usetrademarket.domain.model.categoryList
import com.example.usetrademarket.presentation.view.adapter.ProductListFragmentPagerAdapter
import com.example.usetrademarket.presentation.base.BaseFragment
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.utils.extensions.toVisible
import com.example.usetrademarket.presentation.view.main.MainActivity
import com.example.usetrademarket.presentation.view.product.ProductListFragment
import com.example.usetrademarket.presentation.view.registration.ProductRegistrationActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var viewPagerAdapter : ProductListFragmentPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initViewPager()
        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        goRegistration.observe(viewLifecycleOwner, EventObserver {
            startActivity(ProductRegistrationActivity.newIntent(requireContext()))
        })
    }

    private fun initViewPager() = with(binding) {
        if (::viewPagerAdapter.isInitialized.not()) {
            val productListFragmentList = categoryList.map {
                ProductListFragment.newInstance(it.id, it.name)
            }
            viewPagerAdapter = ProductListFragmentPagerAdapter(
                this@HomeFragment,
                productListFragmentList
            )
            viewPager.adapter = viewPagerAdapter
            viewPager.offscreenPageLimit = productListFragmentList.size

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = categoryList[position].name
            }.attach()
        }
    }

    companion object {

        fun newInstance() = HomeFragment()
        const val TAG = "HomeFragment"
    }
}