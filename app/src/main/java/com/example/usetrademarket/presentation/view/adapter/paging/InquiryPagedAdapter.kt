package com.example.usetrademarket.presentation.view.adapter.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.usetrademarket.R
import com.example.usetrademarket.data.model.response.InquiryResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.ViewholderInquiryItemBinding
import com.example.usetrademarket.presentation.utils.extensions.toGone
import com.example.usetrademarket.presentation.utils.extensions.toVisible

class InquiryPagedAdapter(
    private val inquiryItemClickListener: InquiryItemClickListener,
    private val appPreferenceManager: AppPreferenceManager,
) : PagedListAdapter<InquiryResponse, InquiryPagedAdapter.InquiryItemViewHolder>(
    diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquiryItemViewHolder {
        val binding: ViewholderInquiryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_inquiry_item,
            parent,
            false
        )
        return InquiryItemViewHolder(inquiryItemClickListener, binding)
    }

    override fun onBindViewHolder(holder: InquiryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class InquiryItemViewHolder(
        private val listener: InquiryItemClickListener,
        private val binding: ViewholderInquiryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        var inquiry: InquiryResponse? = null

        init {
            binding.root.setOnClickListener {
                listener.onClickInquiry(inquiry)
            }
        }

        fun bind(item: InquiryResponse?) = item?.let {
            binding.item = it
            this.inquiry = it
            binding.answerButton.setOnClickListener {
                listener.onClickAnswer(inquiry)
            }
            Log.d("InquiryAdapter", "productOwnerId == ${it.productOwnerId}")
            if (it.productOwnerId == appPreferenceManager.getUserId() &&
                binding.answerTextView.text.isNullOrEmpty()
            ) {
                binding.answerButton.toVisible()
            } else {
                binding.answerButton.toGone()
            }
            if (binding.answerTextView.text.isNullOrEmpty()) {
                binding.answerWrapper.toVisible()
            }
        }
    }

    interface InquiryItemClickListener {
        fun onClickInquiry(inquiryResponse: InquiryResponse?)
        fun onClickAnswer(inquiryResponse: InquiryResponse?)
    }

    interface InquiryLiveDataBuilder : LiveDataPagedListBuilder<Long, InquiryResponse>

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<InquiryResponse>() {
            override fun areItemsTheSame(
                oldItem: InquiryResponse,
                newItem: InquiryResponse,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: InquiryResponse,
                newItem: InquiryResponse,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}