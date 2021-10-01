package com.example.usetrademarket.presentation.utils.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.usetrademarket.presentation.utils.extensions.fromDpToPx

// fade 애니메이션 추가
private val factory = DrawableCrossFadeFactory
    .Builder().setCrossFadeEnabled(true).build()

fun ImageView.clear() = Glide.with(this.context).clear(this)

fun ImageView.load(
    url: String,
    corner: Float = 0f,
    scaleType: Transformation<Bitmap> = CenterInside()
) {
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (corner > 0) transforms(scaleType, RoundedCorners(corner.fromDpToPx()))
        }
        .into(this)
}

fun ImageView.loadOrNull(
    url: String?,
    corner: Float = 0f,
    scaleType: Transformation<Bitmap> = CenterInside()
) {
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            if (corner > 0) transforms(scaleType, RoundedCorners(corner.fromDpToPx()))
        }
        .into(this)
}