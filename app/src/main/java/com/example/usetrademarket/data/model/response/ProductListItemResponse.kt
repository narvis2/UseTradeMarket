package com.example.usetrademarket.data.model.response

data class ProductListItemResponse(
    val id : Long,
    val name : String,
    val description : String,
    val price : Int,
    val status : String,
    val sellerId : Long,
    val imagePaths : List<String>,
    val sellerName: String
)

// TODO: 9/27/21 서버에서 가입할때 UserName 가져와서 상품에 SellerName 표시하기