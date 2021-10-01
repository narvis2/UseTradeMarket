package com.example.usetrademarket.presentation.view.adapter.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.usetrademarket.R
import com.example.usetrademarket.data.api.ApiClient
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.ViewholderProductsItemBinding


class ProductListPagedAdapter(
    private val listener :  OnItemCLickListener
) : PagedListAdapter<ProductListItemResponse, ProductListPagedAdapter.ProductItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val binding : ViewholderProductsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_products_item,
            parent,
            false
        )
        return ProductItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ProductItemViewHolder(
        private val binding : ViewholderProductsItemBinding,
        private val listener : OnItemCLickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        var productId : Long? = null

        init {
            binding.root.setOnClickListener {
                listener.onClickProduct(productId)
            }
        }

        fun bind(item: ProductListItemResponse) {
            binding.data = item
            this.productId = item.id
            Glide.with(binding.productImage)
                .load("${ApiClient.BASE_URL}${item.imagePaths.firstOrNull()}")
                .centerCrop()
                .into(binding.productImage)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ProductListItemResponse>() {
            override fun areItemsTheSame(
                oldItem: ProductListItemResponse,
                newItem: ProductListItemResponse,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductListItemResponse,
                newItem: ProductListItemResponse,
            ): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }

    interface OnItemCLickListener {
        fun onClickProduct(productId :Long?)
    }

    interface ProductLiveDataBuilder : LiveDataPagedListBuilder<Long, ProductListItemResponse>
}