package com.example.usetrademarket.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ViewholderGalleryPhotoItemBinding
import com.example.usetrademarket.domain.model.GalleryPhoto

class GalleryPhotoListAdapter(
    private val checkPhotoListener: (GalleryPhoto) -> Unit
) : RecyclerView.Adapter<GalleryPhotoListAdapter.PhotoItemViewHolder>() {

    private var galleryPhotoList: List<GalleryPhoto> = listOf()

    inner class PhotoItemViewHolder(
        private val binding: ViewholderGalleryPhotoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: GalleryPhoto) = with(binding) {
            binding.data = data
//            photoImageView.load(data.uri.toString(), scaleType = CenterCrop())
//            checkButton.setImageDrawable(
//                ContextCompat.getDrawable(
//                    binding.root.context,
//                    if (data.isSelected)
//                        R.drawable.ic_check_enabled
//                    else
//                        R.drawable.ic_check_disabled
//                )
//            )
        }

        fun bindViews(data: GalleryPhoto) = with(binding) {
            root.setOnClickListener {
                checkPhotoListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val view : ViewholderGalleryPhotoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_gallery_photo_item,
            parent,
            false
        )
        return PhotoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bindData(galleryPhotoList[position])
        holder.bindViews(galleryPhotoList[position])
    }

    override fun getItemCount(): Int = galleryPhotoList.size

    fun setPhotoList(galleryPhotoList: List<GalleryPhoto>) {
        this.galleryPhotoList = galleryPhotoList
        notifyDataSetChanged()
    }
}
