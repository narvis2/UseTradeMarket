package com.example.usetrademarket.presentation.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.data.repository.daatasourceimpl.ProductListItemDataSource
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import com.example.usetrademarket.presentation.view.adapter.paging.ProductListPagedAdapter

class ProductSearchViewModel(
    private val keyword : String?,
    private val useTradeApiService: UseTradeApiService
) : BaseViewModel(),
    ProductListPagedAdapter.ProductLiveDataBuilder,
    ProductListPagedAdapter.OnItemCLickListener
{
    val products = buildPagedList()

    private val _goDetail = MutableLiveData<Event<Long?>>()
    val goDetail : LiveData<Event<Long?>>
        get() = _goDetail

    override fun createDataSource(): DataSource<Long, ProductListItemResponse> {
        return ProductListItemDataSource(null, keyword, useTradeApiService)
    }

    override fun onClickProduct(productId: Long?) {
        _goDetail.postValue(Event(productId))
    }
}