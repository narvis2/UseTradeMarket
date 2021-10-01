package com.example.usetrademarket.presentation.view.signin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivitySigninBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.view.main.MainActivity
import com.example.usetrademarket.presentation.view.signup.SignupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SigninActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {

    private val viewModel : SigninViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        binding.signupButton.setOnClickListener {
            startActivity(SignupActivity.newIntent(this))
        }
        initViewModelCallBack()
    }

    private fun initViewModelCallBack() = with(viewModel) {
        isValidEmail.observe(this@SigninActivity, EventObserver {
            showToast("존재하지 않는 이메일입니다.")
        })

        isNotValidPassword.observe(this@SigninActivity, EventObserver{
            showToast("비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        })

        successLogin.observe(this@SigninActivity, EventObserver {
            showToast("로그인에 성공하였습니다.")
            startActivity(MainActivity.newIntent(this@SigninActivity))
            finish()
        })

        error.observe(this@SigninActivity, EventObserver{
            showToast(it)
        })
    }

    companion object {
        fun newIntent(activity: Activity) = Intent(activity, SigninActivity::class.java)
    }
}