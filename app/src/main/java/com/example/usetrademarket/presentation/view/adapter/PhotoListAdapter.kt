package com.example.usetrademarket.presentation.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ViewholderPhotoItemBinding

class PhotoListAdapter(
    private val removePhotoListener: (Uri) -> Unit
) : RecyclerView.Adapter<PhotoListAdapter.ProductItemViewHolder>() {

    private var imageUriList: List<Uri> = listOf()

    inner class ProductItemViewHolder(
        private val binding: ViewholderPhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Uri) = with(binding) {
            binding.data = data
//            photoImageView.load(data.toString(), 8f)
        }

        fun bindViews(data: Uri) = with(binding) {
            closeButton.setOnClickListener {
                removePhotoListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view : ViewholderPhotoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_photo_item,
            parent,
            false
        )
        return ProductItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bindData(imageUriList[position])
        holder.bindViews(imageUriList[position])
    }

    override fun getItemCount(): Int = imageUriList.size

    fun setPhotoList(imageUriList: List<Uri>) {
        this.imageUriList = imageUriList
        notifyDataSetChanged()
    }
}
