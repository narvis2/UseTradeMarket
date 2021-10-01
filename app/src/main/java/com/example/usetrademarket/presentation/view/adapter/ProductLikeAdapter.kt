package com.example.usetrademarket.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.usetrademarket.R
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.databinding.ViewholderLikeProductBinding
import com.example.usetrademarket.domain.model.ProductLikeModel

class ProductLikeAdapter : ListAdapter<ProductLikeModel, ProductLikeAdapter.LikeViewHolder>(
    diffUtil) {

    var onItemClickListener : ((ProductLikeModel) -> Unit)? = null
    var onFavoriteClickListener: ((ProductLikeModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val binding: ViewholderLikeProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_like_product,
            parent,
            false
        )
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class LikeViewHolder(
        private val binding: ViewholderLikeProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(currentList[adapterPosition])
            }
            binding.likeImageButton.setOnClickListener {
                onFavoriteClickListener?.invoke(currentList[adapterPosition])
            }
        }

        fun bind(data: ProductLikeModel) {
            binding.data = data
            binding.executePendingBindings()
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductLikeModel>() {
            override fun areItemsTheSame(
                oldItem: ProductLikeModel,
                newItem: ProductLikeModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductLikeModel,
                newItem: ProductLikeModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}