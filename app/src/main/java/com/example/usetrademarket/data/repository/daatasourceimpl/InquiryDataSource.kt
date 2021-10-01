package com.example.usetrademarket.data.repository.daatasourceimpl

import androidx.paging.PageKeyedDataSource
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.InquiryResponse
import com.example.usetrademarket.data.repository.daatasourceimpl.ProductListItemDataSource.Companion.NEXT
import com.example.usetrademarket.data.repository.daatasourceimpl.ProductListItemDataSource.Companion.PREV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class InquiryDataSource(
    private val productId: Long? = null,
    private val requestUserId: Long? = null,
    private val productOwnerId: Long? = null,
    private val useTradeApiService: UseTradeApiService
) : PageKeyedDataSource<Long, InquiryResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, InquiryResponse>,
    ) {
        val response = getInquiries(Long.MAX_VALUE, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id, it.last().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) {
                throw Exception(response.message)
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, InquiryResponse>,
    ) {
        val response = getInquiries(params.key, PREV)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) { throw Exception("알 수 없는 오류 발생") }
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, InquiryResponse>,
    ) {
        val response = getInquiries(params.key, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.last().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) { throw Exception("알 수 없는 오류 발생") }
        }
    }


    private fun getInquiries(id: Long, direction: String) = runBlocking {
        try {
            useTradeApiService.getInquiries(
                id,
                productId,
                requestUserId,
                productOwnerId,
                direction
            )
        }catch (e:Exception) {
            ApiResponse.error<List<InquiryResponse>>(
                "알 수 없는 오류가 발생했습니다."
            )
        }
    }

}