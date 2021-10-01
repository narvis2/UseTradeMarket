package com.example.usetrademarket.presentation.view.myinquiryList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.FragmentInquiryListBinding
import com.example.usetrademarket.domain.model.InquiryPage
import com.example.usetrademarket.presentation.base.BaseFragment
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.utils.extensions.toGone
import com.example.usetrademarket.presentation.utils.extensions.toVisible
import com.example.usetrademarket.presentation.view.adapter.paging.InquiryPagedAdapter
import com.example.usetrademarket.presentation.view.detail.ProductDetailActivity
import com.example.usetrademarket.presentation.view.inquiryregistration.InquiryRegistrationActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InquiryListFragment : BaseFragment<FragmentInquiryListBinding>(R.layout.fragment_inquiry_list) {

    private val viewModel : InquiryListViewModel by viewModel()

    private val appPreferenceManager : AppPreferenceManager by inject()

    val inquiryPage get() = arguments?.getString(PAGE)?.let {
        InquiryPage.valueOf(it)
    }

    private lateinit var adapter : InquiryPagedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.page = inquiryPage

        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        adapter = InquiryPagedAdapter(viewModel, appPreferenceManager)
        recyclerView.adapter = adapter
    }

    private fun initViewModelCallBack() = with(viewModel) {
        inquiries.observe(viewLifecycleOwner, Observer {
            it.dataSource.map {
                println("productOwnerId -> ${it.productOwnerId}")
                println("requestUserid -> ${it.requestUserId}")
            }
            if (it.isEmpty()) {
                binding.emptyText.toVisible()
                binding.recyclerView.toGone()
            } else {
                adapter.submitList(it)
                binding.emptyText.toGone()
                binding.recyclerView.toVisible()
            }
        })
        goProductDetail.observe(viewLifecycleOwner, EventObserver{
            startActivity(ProductDetailActivity.newIntent(requireContext(), it))
        })
        goInquiryRegistration.observe(viewLifecycleOwner, EventObserver{
            startActivity(InquiryRegistrationActivity.newIntent(requireContext(), it))
        })
    }

    companion object {

        const val PAGE = "page"

        fun newInstance(inquiryPage: InquiryPage) =
            InquiryListFragment().apply {
                arguments = Bundle().also {
                    it.putString(PAGE, inquiryPage.name)
                }
            }
    }
}