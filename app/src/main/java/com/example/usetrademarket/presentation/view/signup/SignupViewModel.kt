package com.example.usetrademarket.presentation.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.domain.usecase.SignupUseCase
import com.example.usetrademarket.presentation.base.BaseViewModel
import com.example.usetrademarket.presentation.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signupUseCase: SignupUseCase
) : BaseViewModel() {

    val email = MutableLiveData<String>("")

    val name = MutableLiveData<String>("")

    val password = MutableLiveData<String>("")

    private val _signUpSuccess = MutableLiveData<Event<Unit>>()
    val signUpSuccess : LiveData<Event<Unit>>
        get() = _signUpSuccess

    private val _isNotValidEmail = MutableLiveData<Event<Unit>>()
    val isNotValidEmail : LiveData<Event<Unit>>
        get() = _isNotValidEmail

    private val _isNotValidPassword = MutableLiveData<Event<Unit>>()
    val isNotValidPassword : LiveData<Event<Unit>>
        get() = _isNotValidPassword

    private val _isNotValidName = MutableLiveData<Event<Unit>>()
    val isNotValidName : LiveData<Event<Unit>>
        get() = _isNotValidName

    private val _error = MutableLiveData<Event<String>>()
    val error : LiveData<Event<String>>
        get() = _error

    fun signup() = viewModelScope.launch(Dispatchers.IO) {
        val request = SignupRequest(email.value, password.value, name.value)
        Log.d("SignupViewModel", "email -> ${email.value} " +
                "password -> ${password.value} " +
                "name -> ${name.value}")
        if (isNotValidSignup(request))
            return@launch

        try {
            val response = signupUseCase.execute(request)
            onSignupResponse(response)
        } catch (e:Exception) {
            val response = signupUseCase.execute(request)
            _error.postValue(Event(response.message.toString()))
        }
    }

    private fun onSignupResponse(response: ApiResponse<Void>) {
        if (response.success) {
            _signUpSuccess.postValue(Event(Unit))
            onBackClick()
        } else {
            _error.postValue(Event(response.message.toString()))
        }
    }

    private fun isNotValidSignup(request: SignupRequest) =
        when {
            request.isNotValidEmail() -> {
                _isNotValidEmail.postValue(Event(Unit))
                true
            }
            request.isNotValidPassword() -> {
                _isNotValidPassword.postValue(Event(Unit))
                true
            }
            request.isNotValidName() -> {
                _isNotValidName.postValue(Event(Unit))
                true
            }
            else -> false
        }

}