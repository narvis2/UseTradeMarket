package com.example.usetrademarket.presentation.view.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.FragmentProductListBinding
import com.example.usetrademarket.presentation.base.BaseFragment
import com.example.usetrademarket.presentation.view.adapter.paging.ProductListPagedAdapter
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.view.detail.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductListFragment : BaseFragment<FragmentProductListBinding>(R.layout.fragment_product_list) {

    private val viewModel : ProductListViewModel by viewModel()

    private lateinit var adapter : ProductListPagedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.categoryId = arguments?.getInt(CATEGORY_ID)!!
        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        adapter = ProductListPagedAdapter(viewModel)
        recyclerView.adapter = adapter
    }

    private fun initViewModelCallBack() = with(viewModel){
        error.observe(viewLifecycleOwner, EventObserver{
            showToast(it)
        })
        goDetail.observe(viewLifecycleOwner, EventObserver{
            startActivity(ProductDetailActivity.newIntent(requireContext(), it))
        })
        products.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.createDataSource()
        initViews()
        initViewModelCallBack()
    }

    companion object {

        const val CATEGORY_NAME = "category_title"

        const val CATEGORY_ID = "category_id"

        fun newInstance(categoryId: Int, title: String) : ProductListFragment {
            val bundle = bundleOf(
                CATEGORY_NAME to title,
                CATEGORY_ID to categoryId
            )
            return ProductListFragment().apply {
                arguments = bundle
            }
        }


    }
}