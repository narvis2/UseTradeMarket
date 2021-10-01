package com.example.usetrademarket.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InquiryModel(
    val productId: Long = -1,
    val inquiryId: Long? = null,
    val type: String
) : Parcelable
