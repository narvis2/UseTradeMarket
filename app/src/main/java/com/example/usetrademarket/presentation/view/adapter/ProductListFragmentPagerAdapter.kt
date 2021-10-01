package com.example.usetrademarket.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.usetrademarket.presentation.view.product.ProductListFragment

class ProductListFragmentPagerAdapter(
    fragment : Fragment,
    val fragmentList : List<ProductListFragment>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}