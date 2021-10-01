package com.example.usetrademarket.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.presentation.utils.Event

abstract class BaseViewModel : ViewModel() {

    private var _backClick = MutableLiveData<Event<Unit>>()
    val backClick : LiveData<Event<Unit>>
        get() = _backClick

    /*
    XML 에서 android:visibility="@{vm.isLoading() ? View.VISIBLE : View.GONE}" 이렇게 사용
    <import type="android.view.View" /> 추가
     */

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    fun showProgress() {
        _isLoading.postValue(true)
    }

    fun hideProgress() {
        _isLoading.postValue(false)
    }

    fun onBackClick() {
        _backClick.postValue(Event(Unit))
    }


}