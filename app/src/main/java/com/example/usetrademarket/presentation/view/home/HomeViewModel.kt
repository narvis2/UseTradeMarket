package com.example.usetrademarket.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event

class HomeViewModel : BaseViewModel() {
    private val _goRegistration = MutableLiveData<Event<Unit>>()
    val goRegistration : LiveData<Event<Unit>>
        get() = _goRegistration


    fun goRegistration() {
        _goRegistration.postValue(Event(Unit))
    }
}