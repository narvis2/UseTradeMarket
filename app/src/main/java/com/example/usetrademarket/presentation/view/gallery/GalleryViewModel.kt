package com.example.usetrademarket.presentation.view.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.domain.model.GalleryPhoto
import com.example.usetrademarket.domain.usecase.GetAllPhotosUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val getAllPhotosUseCase: GetAllPhotosUseCase
) : BaseViewModel() {

    private lateinit var photoList: MutableList<GalleryPhoto>

    private val _loadingRecycler = MutableLiveData<Boolean>(false)
    val loadingRecycler : LiveData<Boolean>
        get() = _loadingRecycler

    private val _setRecycler = MutableLiveData<MutableList<GalleryPhoto>>()
    val setRecycler : LiveData<MutableList<GalleryPhoto>>
        get() = _setRecycler

    private val _confirm = MutableLiveData<Event<List<GalleryPhoto>>>()
    val confirm : LiveData<Event<List<GalleryPhoto>>>
        get() = _confirm

    fun fetchData() = viewModelScope.launch {
        showProgress()
        hideRecyclerView()
        photoList = getAllPhotosUseCase.execute()
        hideProgress()
        showRecyclerView()
        _setRecycler.postValue(photoList)
    }

    fun selectPhoto(galleryPhoto: GalleryPhoto) {
        val findGalleryPhoto = photoList.find { it.id == galleryPhoto.id }
        findGalleryPhoto?.let { photo ->
            photoList[photoList.indexOf(photo)] =
                photo.copy(
                    isSelected = photo.isSelected.not()
                )
            hideProgress()
            showRecyclerView()
            _setRecycler.postValue(photoList)
        }
    }

    fun confirmCheckedPhotos() {
        showProgress()
        hideRecyclerView()
        _confirm.postValue(Event(photoList.filter { it.isSelected }))
    }

    fun showRecyclerView() {
        _loadingRecycler.postValue(true)
    }

    fun hideRecyclerView() {
        _loadingRecycler.postValue(false)
    }
}