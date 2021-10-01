package com.example.usetrademarket.presentation.view.myinquiry

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityMyInquiryBinding
import com.example.usetrademarket.domain.model.InquiryPage
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.module.viewModelModule
import com.example.usetrademarket.presentation.view.adapter.MyInquiryPagerAdapter
import com.example.usetrademarket.presentation.view.myinquiryList.InquiryListFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyInquiryActivity : BaseActivity<ActivityMyInquiryBinding>(R.layout.activity_my_inquiry) {

    private val viewModel : MyInquiryViewModel by viewModel()

    private lateinit var viewPagerAdapter : MyInquiryPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        myInquiryToolbar.setNavigationOnClickListener {
            finish()
        }
        if (::viewPagerAdapter.isInitialized.not()) {
            val fragments = InquiryPage.values().map { InquiryListFragment.newInstance(it) }
            viewPagerAdapter = MyInquiryPagerAdapter(
                this@MyInquiryActivity,
                fragments
            )

            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(
                binding.tabLayout,
                binding.viewPager
            ) { tab, position ->
                tab.setText(fragments[position].inquiryPage?.title)
            }.attach()

            fragments.forEach {
                toolbarTitle.setText(it.inquiryPage?.title)
            }
        }
    }

    private fun initViewModelCallBack() = with(viewModel) {

    }

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, MyInquiryActivity::class.java)
    }
}