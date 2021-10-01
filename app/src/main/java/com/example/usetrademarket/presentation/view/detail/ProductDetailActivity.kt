package com.example.usetrademarket.presentation.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityProductDetailBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.utils.MenuChangeEventBus
import com.example.usetrademarket.presentation.view.adapter.ImageSliderAdapter
import com.example.usetrademarket.presentation.view.inquiry.ProductInquiryActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {

    private val viewModel: ProductDetailViewModel by viewModel {
        parametersOf(intent.getLongExtra(PRODUCT_ID, -1))
    }

    private lateinit var adapter : ImageSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        viewModel.loadDetail()
        viewModel.fetchData()

        initViews()
        viewModelCallBack()
    }

    private fun initViews() = with(binding) {
        detailToolBar.setNavigationOnClickListener {
            finish()
        }
        adapter = ImageSliderAdapter()
        imageViewPager.adapter = adapter
    }

    private fun viewModelCallBack() = with(viewModel) {
        error.observe(this@ProductDetailActivity, EventObserver {
            showToast(it)
        })
        imageUrls.observe(this@ProductDetailActivity, Observer {
            adapter.updateItems(it)
        })
        openInquiryActivity.observe(this@ProductDetailActivity, EventObserver {
            startActivity(ProductInquiryActivity.newIntent(this@ProductDetailActivity, it))
        })
        exchange.observe(this@ProductDetailActivity, Observer {
            binding.likeText.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(this@ProductDetailActivity, if (it == true) {
                    R.drawable.ic_heart_enable
                } else {
                    R.drawable.ic_heart_disable
                }),
                null, null, null
            )
        })
    }

    companion object {

        const val PRODUCT_ID = "productid"

        fun newIntent(context: Context, productId: Long?) =
            Intent(context, ProductDetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(PRODUCT_ID, productId)
            }
    }
}