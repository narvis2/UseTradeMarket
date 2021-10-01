package com.example.usetrademarket.presentation.view.inquiryregistration

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.usetrademarket.R
import com.example.usetrademarket.databinding.ActivityInquiryRegistrationBinding
import com.example.usetrademarket.domain.model.InquiryModel
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InquiryRegistrationActivity : BaseActivity<ActivityInquiryRegistrationBinding>(
    R.layout.activity_inquiry_registration
) {

    private val viewModel : InquiryRegistrationViewModel by viewModel {
        parametersOf(intent.getParcelableExtra<InquiryModel>(INQUIRY_MODEL))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        initViews()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
        inquiryRegistrationToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViewModelCallBack() = with(viewModel) {
        error.observe(this@InquiryRegistrationActivity, EventObserver {
            showToast(it)
        })
        success.observe(this@InquiryRegistrationActivity, EventObserver {
            showToast(it)
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    companion object {

        const val TYPE_ANSWER = "ANSWER"

        const val TYPE_QUESTION = "QUESTION"

        const val INQUIRY_MODEL = "inquiryModel"

        fun newIntent(context: Context, inquiryModel: InquiryModel) =
            Intent(context, InquiryRegistrationActivity::class.java).apply {
                putExtra(INQUIRY_MODEL, inquiryModel)
            }
    }
}