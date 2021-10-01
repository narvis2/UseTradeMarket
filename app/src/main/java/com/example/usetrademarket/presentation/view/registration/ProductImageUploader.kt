package com.example.usetrademarket.presentation.view.registration

import android.content.Intent
import android.content.pm.PackageManager
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductImageUploadResponse
import com.example.usetrademarket.domain.usecase.ProductUploadImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.lang.Exception

class ProductImageUploader(
    private val productUploadImagesUseCase: ProductUploadImagesUseCase
){

    suspend fun upload(imageFile: File) = try {
        val part = makeImagePart(imageFile)
        withContext(Dispatchers.IO) {
            productUploadImagesUseCase.execute(part)
        }
    }catch (e:Exception) {
        ApiResponse.error<ProductImageUploadResponse>(
            "알 수 없는 오류가 발생하였습니다."
        )
    }

    private fun makeImagePart(imageFile : File) : MultipartBody.Part {
        val mediaType = "multipart/form-data".toMediaTypeOrNull()
        val body = imageFile.asRequestBody(mediaType)

        return MultipartBody.Part
            .createFormData("image", imageFile.name, body)
    }
}