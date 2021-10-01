package com.example.usetrademarket.presentation.view.inquiryregistration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.domain.model.InquiryModel
import com.example.usetrademarket.domain.usecase.RegisterInquiryUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class InquiryRegistrationViewModel(
    private val inquiryModel: InquiryModel,
    private val registerInquiryUseCase: RegisterInquiryUseCase
) : BaseViewModel() {

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>>
        get() = _error

    private val _success = MutableLiveData<Event<String>>()
    val success : LiveData<Event<String>>
        get() = _success

    val content = MutableLiveData("")

    fun register() = viewModelScope.launch(Dispatchers.IO) {
        val response = registerInquiry()
        if (!response.success) {
            _error.postValue(Event(response.message ?: "알 수 없는 오류가 발생하였습니다."))
        } else {
            _success.postValue(Event("문의가 등록되었습니다."))
        }
    }

    private suspend fun registerInquiry() = try {
        val request = validateParamsAndMakeRequest()

        if (request.isContentEmpty) ApiResponse.error<Response<Void>>("내용을 입력해주세요.")
        else registerInquiryUseCase.execute(request)
    } catch (e:Exception) {
        ApiResponse.error<Response<Void>>("알 수 없는 오류가 발생했습니다.")
    }

    private fun validateParamsAndMakeRequest() : InquiryRequest {
        val type = inquiryModel.type ?: throw IllegalStateException("inquiryType = null")
        val pid = inquiryModel.productId.let {
            if (it == -1L) throw IllegalStateException("잘못된 productId")
            else it
        }
        val questionId = if (inquiryModel.inquiryId == -1L) null else inquiryModel.inquiryId

        return InquiryRequest(type, questionId, pid, content.value)
    }
}