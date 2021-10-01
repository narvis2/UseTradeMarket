package com.example.usetrademarket.presentation.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.domain.usecase.GetIntroTextUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import com.google.gson.annotations.Until
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroViewModel(
    private val getIntroTextUseCase: GetIntroTextUseCase,
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    private val _introText = MutableLiveData<String>()
    val introText : LiveData<String>
        get() = _introText

    private val _test = MutableLiveData<Unit>()
    val test : LiveData<Unit>
        get() = _test

    private val _goSignin = MutableLiveData<Event<Unit>>()
    val goSignin : LiveData<Event<Unit>>
    get() = _goSignin

    fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        val result = getIntroTextUseCase.execute()
        _introText.postValue(result)

        delay(3000)
        if (appPreferenceManager.getToken().isNullOrEmpty()) {
            _goSignin.postValue(Event(Unit))
        } else {
            onBackClick()
        }
    }

}