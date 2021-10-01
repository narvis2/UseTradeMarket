package com.example.usetrademarket.presentation.view.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String>
        get() = _nickName

    private val _logOut = MutableLiveData<Event<Unit>>()
    val logOut : LiveData<Event<Unit>>
        get() = _logOut

    fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        _email.postValue(appPreferenceManager.getUserEmail())
        _nickName.postValue(appPreferenceManager.getUserName())
    }

    fun logOut() = viewModelScope.launch(Dispatchers.Main) {
        appPreferenceManager.removeToken()
        appPreferenceManager.removeRefreshToken()
        appPreferenceManager.removeUserEmail()
        appPreferenceManager.removeUserName()
        _logOut.postValue(Event(Unit))
    }
}