package com.example.usetrademarket.presentation.view.myinquiryList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.InquiryResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.data.repository.daatasourceimpl.InquiryDataSource
import com.example.usetrademarket.domain.model.InquiryModel
import com.example.usetrademarket.domain.model.InquiryPage
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import com.example.usetrademarket.presentation.view.adapter.paging.InquiryPagedAdapter

class InquiryListViewModel(
    private val appPreferenceManager: AppPreferenceManager,
    private val useTradeApiService: UseTradeApiService
) : BaseViewModel(),
    InquiryPagedAdapter.InquiryItemClickListener {

    var page: InquiryPage? = null
    var requestUserId: Long? = null
    var productOwnerId: Long? = null

    private val _goProductDetail = MutableLiveData<Event<Long>>()
    val goProductDetail: LiveData<Event<Long>>
        get() = _goProductDetail

    private val _goInquiryRegistration = MutableLiveData<Event<InquiryModel>>()
    val goInquiryRegistration: LiveData<Event<InquiryModel>>
        get() = _goInquiryRegistration

    val inquiries by lazy {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        val factory =
            object : DataSource.Factory<Long, InquiryResponse>() {
                override fun create(): DataSource<Long, InquiryResponse> {
                    when (page) {
                        InquiryPage.MY_INQUIRY -> {
                            requestUserId = appPreferenceManager.getUserId()
                        }
                        InquiryPage.PRODUCT_INQUIRY -> {
                            productOwnerId = appPreferenceManager.getUserId()
                        }
                    }
                    return InquiryDataSource(
                        requestUserId = requestUserId,
                        productOwnerId = productOwnerId,
                        useTradeApiService = useTradeApiService
                    )
                }
            }

        LivePagedListBuilder(factory, config).build()
    }

    override fun onClickInquiry(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run {
            _goProductDetail.postValue(Event(productId))
        }
    }

    override fun onClickAnswer(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run {
            val model = InquiryModel(productId, id, "ANSWER")
            _goInquiryRegistration.postValue(Event(model))
        }
    }
}