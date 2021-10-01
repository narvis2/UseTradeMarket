package com.example.usetrademarket.presentation.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.mapper.toProductEntity
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductResponse
import com.example.usetrademarket.domain.usecase.DeleteSelectedLikedProductUseCase
import com.example.usetrademarket.domain.usecase.GetProductUseCase
import com.example.usetrademarket.domain.usecase.GetSelectedLikedProductUseCase
import com.example.usetrademarket.domain.usecase.SaveLikeProductUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import com.example.usetrademarket.presentation.utils.SOLD_OUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat

class ProductDetailViewModel(
    private val productId : Long,
    private val getProductUseCase: GetProductUseCase,
    private val getSelectedLikedProductUseCase: GetSelectedLikedProductUseCase,
    private val deleteSelectedLikedProductUseCase: DeleteSelectedLikedProductUseCase,
    private val saveLikeProductUseCase: SaveLikeProductUseCase
) : BaseViewModel() {

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>>
        get() = _error

    private val _productName = MutableLiveData("-")
    val productName : LiveData<String>
        get() = _productName

    private val _description = MutableLiveData("")
    val description : LiveData<String>
        get() = _description

    private val _price = MutableLiveData("-")
    val price : LiveData<String>
        get() = _price

    private val _openInquiryActivity = MutableLiveData<Event<Long>>()
    val openInquiryActivity : LiveData<Event<Long>>
        get() = _openInquiryActivity

    private val _exchange = MutableLiveData<Boolean>(false)
    val exchange : LiveData<Boolean>
        get() = _exchange

    val imageUrls : MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf())

    fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        val result = getSelectedLikedProductUseCase.execute(productId) != null
        _exchange.postValue(result)
    }

    fun loadDetail() = viewModelScope.launch(Dispatchers.Main) {
        try {
            val response = getProduct(productId)
            if (response.success && response.data != null) {
                updateViewData(response.data)
            } else {
                _error.postValue(Event(response.message ?: "알 수 없는 오류가 발생하였습니다."))
            }
        }catch (e: Exception) {
            _error.postValue(Event(e.message ?: "알 수 없는 오류가 발생했습니다."))
        }
    }

    fun putShoppingCart() = viewModelScope.launch(Dispatchers.IO) {
        val result = getSelectedLikedProductUseCase.execute(productId)
        Log.d("detailViewModel", "result -> $result")
        result?.let {
            deleteSelectedLikedProductUseCase.execute(it.id)
            _exchange.postValue(false)
            Log.d("detailViewModel", "삭제되었습니다.")
        } ?: kotlin.run {
            getProduct(productId).data?.let {
                saveLikeProductUseCase.execute(it.toProductEntity())
                Log.d("detailViewModel", "저장되었습니다.")
            }
            _exchange.postValue(true)
        }
    }

    fun openInquiryActivity() {
        _openInquiryActivity.postValue(Event(productId))
    }

    private suspend fun getProduct(id: Long) = withContext(Dispatchers.IO) {
        try {
            getProductUseCase.execute(id)
        }catch (e:Exception) {
            _error.postValue(Event("상품 정보를 가져오는 중 오류가 발생하였습니다."))
            ApiResponse.error<ProductResponse>("상품 정보를 가져오는 중 오류가 발생하였습니다.")
        }
    }

    private fun updateViewData(product: ProductResponse) {
        val commaSeparatedPrice =
            NumberFormat.getInstance().format(product.price)
        val soldOutString =
            if (SOLD_OUT == product.status) "(품절)" else ""

        _productName.postValue(product.name)
        _description.postValue(product.description)
        _price.postValue("₩${commaSeparatedPrice} $soldOutString")
        imageUrls.addAll(product.imagePaths)
    }

    private fun <T> MutableLiveData<MutableList<T>>.addAll(item: List<T>) {
        this.value?.addAll(item)
        this.value = this.value
    }
}