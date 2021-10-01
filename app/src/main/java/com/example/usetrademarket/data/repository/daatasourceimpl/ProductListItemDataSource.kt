package com.example.usetrademarket.data.repository.daatasourceimpl

import androidx.paging.PageKeyedDataSource
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import kotlinx.coroutines.*

class ProductListItemDataSource(
    private val categoryId: Int?,
    private val keyword : String?,
    private val useTradeApiService: UseTradeApiService
) : PageKeyedDataSource<Long, ProductListItemResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, ProductListItemResponse>,
    ) {
        val response = getProducts(Long.MAX_VALUE, NEXT)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id, it.last().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) {
                throw Exception("알 수 없는 오류 발생")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>,
    ) {
        val response = getProducts(params.key, PREV)
        if (response.success) {
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.first().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) {
                throw Exception("알 수 없는 오류 발생")
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ProductListItemResponse>,
    ) {
        val response = getProducts(params.key , NEXT)
        if (response.success){
            response.data?.let {
                if (it.isNotEmpty())
                    callback.onResult(it, it.last().id)
            }
        } else {
            MainScope().launch(Dispatchers.Main) {
                throw Exception("알 수 없는 오류 발생")
            }
        }
    }

    private fun getProducts(id: Long, direction: String) = runBlocking {
        try {
            withContext(Dispatchers.IO) {
                useTradeApiService.getProducts(id, categoryId, direction, keyword)
            }
        }catch (e:Exception){
            ApiResponse.error<List<ProductListItemResponse>>("페이징 도중 알 수 없는 오류 발생")
        }
    }

    companion object{
        const val NEXT = "next"
        const val PREV = "prev"
    }
}