package com.example.usetrademarket.presentation.view.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivitySignupBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : BaseActivity<ActivitySignupBinding>(
    R.layout.activity_signup
) {

    private val viewModel : SignupViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        isNotValidEmail.observe(this@SignupActivity, EventObserver {
            showToast(getString(R.string.is_not_valide_email))
        })
        isNotValidName.observe(this@SignupActivity, EventObserver {
            showToast(getString(R.string.is_not_valid_name))
        })
        isNotValidPassword.observe(this@SignupActivity, EventObserver {
            showToast(getString(R.string.is_not_valid_password))
        })
        signUpSuccess.observe(this@SignupActivity, EventObserver {
            showToast(getString(R.string.success_sign_up))
        })
        backClick.observe(this@SignupActivity, EventObserver {
            finish()
        })
        error.observe(this@SignupActivity, EventObserver {
            showToast(it)
        })
    }

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, SignupActivity::class.java)
    }

}