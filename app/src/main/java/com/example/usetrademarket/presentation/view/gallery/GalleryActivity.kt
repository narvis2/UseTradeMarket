package com.example.usetrademarket.presentation.view.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityGalleryBinding
import com.example.usetrademarket.presentation.view.adapter.GalleryPhotoListAdapter
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.utils.dicoration.GridDividerDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryActivity : BaseActivity<ActivityGalleryBinding>(R.layout.activity_gallery) {

    private val viewModel : GalleryViewModel by viewModel()

    private val adapter = GalleryPhotoListAdapter {
        viewModel.selectPhoto(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViews()
        viewModel.fetchData()
        initViewModelCallback()
    }

    private fun initViews() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridDividerDecoration(this@GalleryActivity, R.drawable.bg_frame_gallery))
        confirmButton.setOnClickListener {
            viewModel.confirmCheckedPhotos()
        }
    }

    private fun initViewModelCallback() = with(viewModel) {
        setRecycler.observe(this@GalleryActivity, Observer{
            adapter.setPhotoList(it.toList())
        })
        confirm.observe(this@GalleryActivity, EventObserver { photoList ->
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(URI_LIST_KEY, ArrayList(photoList.map { it.uri }))
            })
            finish()
        })
    }

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, GalleryActivity::class.java)

        const val URI_LIST_KEY = "uriList"
    }
}