package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.domain.model.InquiryModel
import com.example.usetrademarket.presentation.view.detail.ProductDetailViewModel
import com.example.usetrademarket.presentation.view.gallery.GalleryViewModel
import com.example.usetrademarket.presentation.view.home.HomeViewModel
import com.example.usetrademarket.presentation.view.inquiry.InquiryViewModel
import com.example.usetrademarket.presentation.view.inquiryregistration.InquiryRegistrationViewModel
import com.example.usetrademarket.presentation.view.intro.IntroViewModel
import com.example.usetrademarket.presentation.view.like.MyProductFavoriteViewModel
import com.example.usetrademarket.presentation.view.main.MainViewModel
import com.example.usetrademarket.presentation.view.myinquiry.MyInquiryViewModel
import com.example.usetrademarket.presentation.view.myinquiryList.InquiryListViewModel
import com.example.usetrademarket.presentation.view.product.ProductListViewModel
import com.example.usetrademarket.presentation.view.profile.MyProfileViewModel
import com.example.usetrademarket.presentation.view.registration.ProductRegistrationViewModel
import com.example.usetrademarket.presentation.view.search.ProductSearchViewModel
import com.example.usetrademarket.presentation.view.signin.SigninViewModel
import com.example.usetrademarket.presentation.view.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule : Module = module {
    viewModel<MainViewModel> { MainViewModel() }
    viewModel<IntroViewModel> { IntroViewModel(get(), get()) }
    viewModel<SignupViewModel> { SignupViewModel(get()) }
    viewModel<SigninViewModel> { SigninViewModel(get(),get()) }
    viewModel<HomeViewModel> { HomeViewModel() }
    viewModel<ProductRegistrationViewModel> { ProductRegistrationViewModel(get(), get(), get()) }
    viewModel<MyProfileViewModel> { MyProfileViewModel(get()) }
    viewModel<MyProductFavoriteViewModel>{ MyProductFavoriteViewModel(get(),get()) }
    viewModel<ProductListViewModel> { ProductListViewModel(get()) }
    viewModel<GalleryViewModel> { GalleryViewModel(get()) }
    viewModel<ProductDetailViewModel> { (productId : Long) ->
        ProductDetailViewModel(productId, get(), get(), get(), get()) }
    viewModel<ProductSearchViewModel> { (keyword: String?) ->
        ProductSearchViewModel(keyword, get())
    }
    viewModel<InquiryViewModel> { (productId : Long) ->
        InquiryViewModel(productId, get())
    }
    viewModel<InquiryRegistrationViewModel> { (inquiryModel : InquiryModel) ->
        InquiryRegistrationViewModel(inquiryModel, get())
    }
    viewModel<InquiryListViewModel> { InquiryListViewModel(get(), get()) }
    viewModel<MyInquiryViewModel> { MyInquiryViewModel() }
}