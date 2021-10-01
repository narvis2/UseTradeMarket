package com.example.usetrademarket.presentation.utils

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.usetrademarket.R
import com.example.usetrademarket.data.api.ApiClient
import com.example.usetrademarket.data.api.ApiClient.BASE_URL
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.domain.model.GalleryPhoto
import com.example.usetrademarket.domain.model.ProductLikeModel
import com.example.usetrademarket.domain.model.ProductModel
import com.example.usetrademarket.presentation.utils.extensions.load
import com.example.usetrademarket.presentation.utils.extensions.loadOrNull
import com.example.usetrademarket.presentation.view.registration.ProductRegistrationActivity
import com.example.usetrademarket.presentation.view.registration.ProductRegistrationViewModel
import java.text.NumberFormat

@BindingAdapter("android:setGlide")
fun ImageView.setGlide(data: GalleryPhoto) {
    this.load(data.uri.toString(), scaleType = CenterCrop())
}

@BindingAdapter("android:setImage")
fun ImageView.setImage(uri : String) {
    this.load("${BASE_URL}$uri", scaleType = CenterCrop())
}

@BindingAdapter("android:setImages")
fun ImageView.setImageList(imagePaths: List<String>?) {
    this.load("${BASE_URL}${imagePaths?.firstOrNull()}", scaleType = CenterCrop())
}

@BindingAdapter("android:setAnimation")
fun ImageButton.setAnimation(isSelected: Boolean) {
    this.setImageDrawable(
        ContextCompat.getDrawable(
            this.context,
            if (isSelected) {
                R.drawable.ic_check_enabled
            } else {
                R.drawable.ic_check_disabled
            }
        )
    )
}

@BindingAdapter("android:setPhotoImageView")
fun ImageView.setPhotoImageView(uri: Uri) {
    this.load(uri.toString(), 8f)
}

@BindingAdapter("android:setProductPrice")
fun TextView.setProductPrice(data: ProductListItemResponse) {
    val soldOutString =
        if (SOLD_OUT == data.status) "(품절)" else ""
    val commaSeparatedPrice =
        NumberFormat.getNumberInstance().format(data.price)
    this.text = "$commaSeparatedPrice $soldOutString"
}
@BindingAdapter("android:setUserProductPrice")
fun TextView.setUserProductPrice(data: ProductModel) {
    val soldOutString =
        if (SOLD_OUT == data.status) "(품절)" else ""
    val commaSeparatedPrice =
        NumberFormat.getNumberInstance().format(data.price)
    this.text = "₩ $commaSeparatedPrice $soldOutString"
}

@BindingAdapter("android:setLikedProductPrice")
fun TextView.setLikedProductPrice(data: ProductLikeModel) {
    val soldOutString =
        if (SOLD_OUT == data.status) "(품절)" else ""
    val commaSeparatedPrice =
        NumberFormat.getNumberInstance().format(data.price)
    this.text = "￦ $commaSeparatedPrice $soldOutString"
}
@BindingAdapter("android:setProductThumbNails")
fun ImageView.setProductThumbNails(data: ProductListItemResponse) {
    data.imagePaths.firstOrNull()?.let {
        Glide.with(this)
            .load(it)
            .centerCrop()
            .into(this)
    }

}

@BindingAdapter("android:onItemSelectedListener")
fun Spinner.onItemSelectedListener(viewModel: ProductRegistrationViewModel) {

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            viewModel.onCategorySelect(position)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
        }
    }
    this.onItemSelectedListener = listener
}
