package com.example.usetrademarket.presentation.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityIntroBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import com.example.usetrademarket.presentation.view.main.MainActivity
import com.example.usetrademarket.presentation.view.signin.SigninActivity
import com.example.usetrademarket.presentation.view.signup.SignupActivity
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : BaseActivity<ActivityIntroBinding>(
    R.layout.activity_intro
) {

    private val viewModel : IntroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        viewModel.fetchData()
        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        backClick.observe(this@IntroActivity, EventObserver {
            startActivity(MainActivity.newIntent(this@IntroActivity))
            finish()
        })
        goSignin.observe(this@IntroActivity, EventObserver{
            startActivity(SigninActivity.newIntent(this@IntroActivity))
            finish()
        })
    }

}