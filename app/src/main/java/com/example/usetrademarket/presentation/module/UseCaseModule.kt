package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.domain.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule : Module = module {
    factory<GetIntroTextUseCase> { GetIntroTextUseCase(get()) }
    factory<SignupUseCase> { SignupUseCase(get()) }
    factory<SigninUseCase> { SigninUseCase(get()) }
    factory<ProductUploadImagesUseCase> { ProductUploadImagesUseCase(get()) }
    factory<GetAllPhotosUseCase> { GetAllPhotosUseCase(get()) }
    factory<RegisterProductUseCase> { RegisterProductUseCase(get()) }
    factory<GetProductsListUseCase> { GetProductsListUseCase(get()) }
    factory<GetProductUseCase> { GetProductUseCase(get()) }
    factory<GetProductsByKeywordUseCase> { GetProductsByKeywordUseCase(get()) }
    factory<RegisterInquiryUseCase> { RegisterInquiryUseCase(get()) }
    factory<GetAllUseLikeProductUseCase> { GetAllUseLikeProductUseCase(get()) }
    factory<GetSelectedLikedProductUseCase> { GetSelectedLikedProductUseCase(get()) }
    factory<SaveLikeProductUseCase> { SaveLikeProductUseCase(get()) }
    factory<DeleteSelectedLikedProductUseCase> { DeleteSelectedLikedProductUseCase(get()) }
    factory<DeleteAllLikedProductUseCase> { DeleteAllLikedProductUseCase(get()) }
}