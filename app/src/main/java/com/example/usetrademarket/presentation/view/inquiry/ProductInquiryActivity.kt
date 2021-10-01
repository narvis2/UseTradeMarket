package com.example.usetrademarket.presentation.view.inquiry

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.ActivityProductInquiryBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.utils.MenuChangeEventBus
import com.example.usetrademarket.presentation.view.adapter.paging.InquiryPagedAdapter
import com.example.usetrademarket.presentation.view.home.HomeFragment
import com.example.usetrademarket.presentation.view.inquiryregistration.InquiryRegistrationActivity
import com.example.usetrademarket.presentation.view.main.MainTabMenu
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductInquiryActivity : BaseActivity<ActivityProductInquiryBinding>(
    R.layout.activity_product_inquiry
) {

    private val productId by lazy {
        intent?.getLongExtra(ProductInquiryActivity.PRODUCT_ID, -1)
    }

    private val viewModel : InquiryViewModel by viewModel {
        parametersOf(productId)
    }

    private val appPreferenceManager : AppPreferenceManager by inject()

    private val menuChangeEventBus : MenuChangeEventBus by inject()

    private lateinit var adapter: InquiryPagedAdapter

    private val getCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                lifecycleScope.launch {
                    menuChangeEventBus.changeMenu(MainTabMenu.HOME)
                }
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        adapter = InquiryPagedAdapter(viewModel, appPreferenceManager)
        recyclerView.adapter = adapter
        inquiryToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViewModelCallBack() = with(viewModel) {
        goRegistrationInquiry.observe(this@ProductInquiryActivity, EventObserver {
            getCallback.launch(
                InquiryRegistrationActivity.newIntent(this@ProductInquiryActivity, it)
            )
        })
        inquiris.observe(this@ProductInquiryActivity, Observer {
            if (it.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyText.visibility = View.VISIBLE
            } else {
                adapter.submitList(it)
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyText.visibility = View.GONE
            }

        })
    }

    companion object {

        const val PRODUCT_ID = "productId"

        fun newIntent(activity: Activity, productId : Long) = Intent(activity, ProductInquiryActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(PRODUCT_ID, productId)
        }

    }
}