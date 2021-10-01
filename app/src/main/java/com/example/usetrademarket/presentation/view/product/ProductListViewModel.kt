package com.example.usetrademarket.presentation.view.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.data.repository.daatasourceimpl.ProductListItemDataSource
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.view.adapter.paging.ProductListPagedAdapter
import com.example.usetrademarket.presentation.utils.Event

class ProductListViewModel(
    private val useTradeApiService: UseTradeApiService
)
    : BaseViewModel(),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemCLickListener {

    var categoryId: Int = -1

    val products = buildPagedList()

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>>
        get() = _error

    private val _goDetail = MutableLiveData<Event<Long?>>()
    val goDetail : LiveData<Event<Long?>>
        get() = _goDetail

    override fun createDataSource(): DataSource<Long, ProductListItemResponse> {
        if (categoryId == -1)
            _error.postValue(Event("categoryId 가 설정되지 않았습니다."))
        return ProductListItemDataSource(categoryId, null, useTradeApiService)
    }

    override fun onClickProduct(productId: Long?) {
        _goDetail.postValue(Event(productId))
    }
}