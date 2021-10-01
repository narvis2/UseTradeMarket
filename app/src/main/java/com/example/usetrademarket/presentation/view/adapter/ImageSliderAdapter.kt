package com.example.usetrademarket.presentation.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usetrademarket.R
import com.example.usetrademarket.data.api.ApiClient
import com.example.usetrademarket.databinding.ViewholderProductImagesBinding

class ImageSliderAdapter : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    var imageUrls: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding : ViewholderProductImagesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_product_images,
            parent,
            false
        )
        return ImageSliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.setImage(imageUrls[position])
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageSliderViewHolder(
        private val binding : ViewholderProductImagesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setImage(url : String) {
            Log.d("ImageSlider", "Url -> $url")
            Glide.with(binding.productImages)
                .load("${ApiClient.BASE_URL}$url")
                .into(binding.productImages)
            binding.executePendingBindings()
        }
    }

    fun updateItems(items: MutableList<String>) {
        imageUrls = items
        notifyDataSetChanged()
    }
}