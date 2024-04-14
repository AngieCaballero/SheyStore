package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.product.IProductRepository
import com.angiedev.sheystore.data.repository.product.ProductRepositoryResponseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductModule {

    @Binds
    abstract fun provideProductModule(productRepositoryResponseImp: ProductRepositoryResponseImp) : IProductRepository
}