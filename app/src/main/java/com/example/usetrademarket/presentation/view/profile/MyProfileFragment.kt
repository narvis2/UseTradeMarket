package com.example.usetrademarket.presentation.view.profile

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.FragmentMyProfileBinding
import com.example.usetrademarket.presentation.base.BaseFragment
import com.example.usetrademarket.presentation.utils.extensions.load
import com.example.usetrademarket.presentation.view.signin.SigninActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>(R.layout.fragment_my_profile) {

    private val viewModel : MyProfileViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        initViews()
        viewModel.fetchData()
        initViewModelCallBack()
    }

    private fun initViews() = with(binding) {
    }

    private fun initViewModelCallBack() = with(viewModel) {
        logOut.observe(viewLifecycleOwner, Observer {
            val intent = Intent(requireContext(), SigninActivity::class.java)
            startActivity(intent)
        })
    }

    companion object {
        const val TAG = "MyProfileFragment"

        fun newInstance() = MyProfileFragment()
    }
}