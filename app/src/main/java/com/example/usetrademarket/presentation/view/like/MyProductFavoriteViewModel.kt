package com.example.usetrademarket.presentation.view.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.mapper.toProductLikeModel
import com.example.usetrademarket.domain.model.ProductLikeModel
import com.example.usetrademarket.domain.usecase.DeleteSelectedLikedProductUseCase
import com.example.usetrademarket.domain.usecase.GetAllUseLikeProductUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyProductFavoriteViewModel(
    private val getAllUseLikeProductUseCase: GetAllUseLikeProductUseCase,
    private val deleteSelectedLikedProductUseCase: DeleteSelectedLikedProductUseCase
) : BaseViewModel() {

    fun getSavedLikeItem() : LiveData<List<ProductLikeModel>> = liveData {

        val response = getAllUseLikeProductUseCase.execute()

        response.collect {
            val result = it.toProductLikeModel()
            emit(result)
        }
    }

    fun dislikeProductItem(productLikeEntity: ProductLikeEntity) = viewModelScope.launch {
        deleteSelectedLikedProductUseCase.execute(productLikeEntity.id)
    }
}