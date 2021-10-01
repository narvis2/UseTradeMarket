package com.example.usetrademarket.presentation.view.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.ActivityProductSearchBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.view.adapter.paging.ProductListPagedAdapter
import com.example.usetrademarket.presentation.view.detail.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductSearchActivity : BaseActivity<ActivityProductSearchBinding>(
    R.layout.activity_product_search
) {

    private val viewModel : ProductSearchViewModel by viewModel {
        parametersOf(intent.getStringExtra(KEYWORD))
    }

    private lateinit var adapter : ProductListPagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViews()
        viewModelCallBack()
    }

    private fun initViews() = with(binding) {
        adapter = ProductListPagedAdapter(viewModel)
        recyclerView.adapter = adapter
        toolbarTitle.text = intent.getStringExtra(KEYWORD)
        searchToolBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun viewModelCallBack() = with(viewModel) {
        goDetail.observe(this@ProductSearchActivity, EventObserver {
            startActivity(ProductDetailActivity.newIntent(this@ProductSearchActivity, it))
        })
        products.observe(this@ProductSearchActivity, Observer {
            if (it.isEmpty().not()) {
                adapter.submitList(it)
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyText.visibility = View.GONE
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.emptyText.visibility = View.VISIBLE
            }
        })
    }

    companion object {
        const val KEYWORD = "keyword"
        fun newIntent(activity : Activity, keyword : String) = Intent(activity, ProductSearchActivity::class.java).apply {
            putExtra(KEYWORD, keyword)
        }
    }
}