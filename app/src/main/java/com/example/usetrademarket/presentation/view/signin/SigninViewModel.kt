package com.example.usetrademarket.presentation.view.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.SigninResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.domain.usecase.SigninUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SigninViewModel(
    private val signinUseCase: SigninUseCase,
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>>
        get() = _error

    private val _isValidEmail = MutableLiveData<Event<Unit>>()
    val isValidEmail : LiveData<Event<Unit>>
        get() = _isValidEmail

    private val _isNotValidPassword = MutableLiveData<Event<Unit>>()
    val isNotValidPassword : LiveData<Event<Unit>>
        get() = _isNotValidPassword

    private val _successLogin = MutableLiveData<Event<Unit>>()
    val successLogin : LiveData<Event<Unit>>
        get() = _successLogin

    fun signin() = viewModelScope.launch(Dispatchers.IO) {
        val request = SigninRequest(email.value, password.value)
        if (isNotValidSignin(request))
            return@launch

        try {
            val response = signinUseCase.execute(request)
            onSigninResponse(response)
        }catch (e:Exception) {
            val response = signinUseCase.execute(request)
            _error.postValue(Event(response.message.toString()))
        }
    }

    private fun onSigninResponse(response: ApiResponse<SigninResponse>) {
        if (response.success && response.data != null) {
            appPreferenceManager.apply {
                setToken(response.data.token)
                setRefreshToken(response.data.refreshToken)
                setUserName(response.data.userName)
                setUserId(response.data.userId)
                setUserEmail(response.data.email)
            }
            _successLogin.postValue(Event(Unit))
        } else {
            _error.postValue(Event(response.message.toString()))
        }
    }

    private fun isNotValidSignin(request: SigninRequest): Boolean =
        when {
            request.isValidEmail() -> {
                _isValidEmail.postValue(Event(Unit))
                true
            }
            request.isNotValidPassword() -> {
                _isNotValidPassword.postValue(Event(Unit))
                true
            }
            else -> false
        }
}