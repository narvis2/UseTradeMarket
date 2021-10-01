package com.example.usetrademarket.presentation.view.registration

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductImageUploadResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.domain.model.categoryList
import com.example.usetrademarket.domain.usecase.ProductUploadImagesUseCase
import com.example.usetrademarket.domain.usecase.RegisterProductUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class ProductRegistrationViewModel(
    private val productUploadImagesUseCase: ProductUploadImagesUseCase,
    private val registerProductUseCase: RegisterProductUseCase,
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    val imageUrls: List<MutableLiveData<String?>> = listOf(
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?)
    )

    var imageUri: Uri? = null

    val imageIds: MutableList<Long?> =
        mutableListOf(null, null, null, null)

    val productName = MutableLiveData<String>("")
    val description = MutableLiveData<String>("")
    val price = MutableLiveData<String>("")
    val categories = MutableLiveData(categoryList.map { it.name })
    var categoryIdSelected: Int? = categoryList[0].id

    var currentImageNum = 0

    private val _pickContentImage = MutableLiveData<Event<Int>>()
    val pickContentImage: LiveData<Event<Int>>
        get() = _pickContentImage

    private val _success = MutableLiveData<Event<String>>()
    val success: LiveData<Event<String>>
        get() = _success

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    private val _validName = MutableLiveData<Event<String>>()
    val validName: LiveData<Event<String>>
        get() = _validName

    private val _validDescription = MutableLiveData<Event<String>>()
    val validDescription: LiveData<Event<String>>
        get() = _validDescription

    private val _validPrice = MutableLiveData<Event<String>>()
    val validPrice: LiveData<Event<String>>
        get() = _validPrice

    private val _validCategory = MutableLiveData<Event<String>>()
    val validCategory: LiveData<Event<String>>
        get() = _validName

    private val _validImageIds = MutableLiveData<Event<String>>()
    val validImageIds: LiveData<Event<String>>
        get() = _validImageIds

    fun onCategorySelect(position: Int) {
        categoryIdSelected = categoryList[position].id
        Log.d("ProductViewModel", "categoryIdSelected -> $categoryIdSelected")
    }

    fun pickImage(imageNum: Int) {

        _pickContentImage.postValue(Event(imageNum))
        currentImageNum = imageNum
    }

    fun uploadImage(file: File?) = viewModelScope.launch(Dispatchers.IO) {
        file?.let {
            val response = upload(it)
            if (response.success && response.data != null) {
                imageUrls[currentImageNum].postValue(response.data.filePath)
                imageIds[currentImageNum] = response.data.productImageId
            }
        }

    }

    suspend fun upload(imageFile: File) = try {
        val part = makeImagePart(imageFile)
        productUploadImagesUseCase.execute(part)
    } catch (e: Exception) {
        ApiResponse.error<ProductImageUploadResponse>(
            "알 수 없는 오류가 발생하였습니다."
        )
    }

    private fun makeImagePart(imageFile: File): MultipartBody.Part {
        val mediaType = "multipart/form-data".toMediaTypeOrNull()
        val body = imageFile.asRequestBody(mediaType)

        return MultipartBody.Part
            .createFormData("image", imageFile.name, body)
    }

    fun register() = viewModelScope.launch(Dispatchers.IO) {
        val request = extractRequest()
        Log.d("ProductViewModel", "ProductRequest -> $request")
        val response = validRegister(request)
        onRegistrationResponse(response)
    }

    private fun extractRequest(): ProductRegistrationRequest  {
        return ProductRegistrationRequest(
            productName.value,
            description.value,
            price.value?.toIntOrNull(),
            categoryIdSelected,
            imageIds,
            appPreferenceManager.getUserName()
        )
    }


    private fun onRegistrationResponse(response: ApiResponse<Response<Void>>) {
        if (response.success) {
            _success.postValue(Event("상품이 등록되었습니다."))
        } else {
            _error.postValue(Event(response.message ?: "알 수 없는 오류가 발생하였습니다."))
        }
    }

    private suspend fun validRegister(request: ProductRegistrationRequest): ApiResponse<Response<Void>> =
        when {
            request.isNotValidName -> {
                _validName.postValue(Event("상품명을 조건에 맞게 입력해주세요."))
                ApiResponse.error("상품명을 조건에 맞게 입력해주세요.")
            }
            request.isNotValidDescription -> {
                _validDescription.postValue(Event("상품 설명을 조건에 맞게 입력해주세요."))
                ApiResponse.error("상품 설명을 조건에 맞게 입력해주세요.")
            }
            request.isNotValidPrice -> {
                _validPrice.postValue(Event("가격을 조건에 맞게 입력해주세요."))
                ApiResponse.error("가격을 조건에 맞게 입력해주세요.")
            }
            request.isNotValidCategoryId -> {
                _validCategory.postValue(Event("카테고리 아이디를 선택해주세요"))
                ApiResponse.error("카테고리 아디리를 선택해주세요.")
            }
            request.isNotValidImageIds -> {
                _validImageIds.postValue(Event("이미지를 한개 이상 등록해주세요."))
                ApiResponse.error("이미지를 한개 이상 등록해주세요.")
            }
            else -> {
                try {
                    registerProductUseCase.execute(request)
                } catch (e: Exception) {
                    ApiResponse.error<Response<Void>>("알 수 없는 오류가 발생하였습니다.")
                }
            }
        }
}