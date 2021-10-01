package com.example.usetrademarket.presentation.view.inquiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.InquiryResponse
import com.example.usetrademarket.data.repository.daatasourceimpl.InquiryDataSource
import com.example.usetrademarket.domain.model.InquiryModel
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import com.example.usetrademarket.presentation.view.adapter.paging.InquiryPagedAdapter
import com.example.usetrademarket.presentation.view.inquiryregistration.InquiryRegistrationActivity
import kotlinx.coroutines.launch

class InquiryViewModel(
    private val productId : Long,
    private val useTradeApiService: UseTradeApiService
) : BaseViewModel(),
InquiryPagedAdapter.InquiryLiveDataBuilder,
InquiryPagedAdapter.InquiryItemClickListener{

    val inquiris = buildPagedList()

    private val _goRegistrationInquiry = MutableLiveData<Event<InquiryModel>>()
    val goRegistrationInquiry : LiveData<Event<InquiryModel>>
        get() = _goRegistrationInquiry

    override fun createDataSource(): DataSource<Long, InquiryResponse> {
        if (productId == -1L)
            IllegalStateException("productId is -1 ")
        return InquiryDataSource(productId, useTradeApiService = useTradeApiService)
    }

    override fun onClickInquiry(inquiryResponse: InquiryResponse?) {

    }

    override fun onClickAnswer(inquiryResponse: InquiryResponse?) {
        inquiryResponse?.run {
            _goRegistrationInquiry.postValue(Event(inquiry(InquiryRegistrationActivity.TYPE_ANSWER, id)))
        }
    }

    fun goQuestion() = viewModelScope.launch {
        _goRegistrationInquiry.postValue(Event(inquiry("QUESTION")))
    }

    private fun inquiry(type: String, inquiryId: Long? = null) : InquiryModel {
        return InquiryModel(productId = productId, inquiryId = inquiryId, type = type)
    }
}