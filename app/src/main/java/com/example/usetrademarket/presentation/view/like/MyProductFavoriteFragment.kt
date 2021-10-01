package com.example.usetrademarket.presentation.view.like

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.data.mapper.toProductLikeEntity
import com.example.usetrademarket.databinding.FragmentMyProductFavoriteBinding
import com.example.usetrademarket.domain.model.ProductLikeModel
import com.example.usetrademarket.presentation.base.BaseFragment
import com.example.usetrademarket.presentation.utils.extensions.toGone
import com.example.usetrademarket.presentation.view.adapter.ProductLikeAdapter
import com.example.usetrademarket.presentation.view.detail.ProductDetailActivity
import com.example.usetrademarket.presentation.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyProductFavoriteFragment : BaseFragment<FragmentMyProductFavoriteBinding>(
    R.layout.fragment_my_product_favorite
) {

    private val viewModel : MyProductFavoriteViewModel by viewModel()

    private lateinit var adapter: ProductLikeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        viewModel.getSavedLikeItem()
        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        adapter = ProductLikeAdapter()
        likeRecyclerView.adapter = adapter
        adapter.onItemClickListener = {
            startActivity(ProductDetailActivity.newIntent(requireContext(), it.id))
        }
        adapter.onFavoriteClickListener = {
            viewModel.dislikeProductItem(it.toProductLikeEntity())
        }
    }

    private fun initViewModelCallBack() = with(viewModel) {
        getSavedLikeItem().observe(viewLifecycleOwner, Observer {
            checkListEmpty(it)
        })
    }

    private fun checkListEmpty(productLikeList: List<ProductLikeModel>) {
        val isEmpty = productLikeList.isEmpty()
        binding.likeRecyclerView.isGone = isEmpty
        binding.emptyResultTextView.isVisible = isEmpty
        if (isEmpty.not()) {
            adapter.submitList(productLikeList)
        }
    }

    companion object {
        const val TAG = "MyProductFavoriteFragment"

        fun newInstance() = MyProductFavoriteFragment()
    }
}