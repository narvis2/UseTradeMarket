package com.example.usetrademarket.data.model.request

data class InquiryRequest(
    val type: String,
    val inquiryId: Long?,
    val productId: Long,
    val content: String?
) {
    val isContentEmpty = content.isNullOrEmpty()
}