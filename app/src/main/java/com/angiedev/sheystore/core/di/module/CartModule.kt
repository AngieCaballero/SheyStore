package com.angiedev.sheystore.core.di.module

import com.angiedev.sheystore.data.repository.cart.CartRepositoryImp
import com.angiedev.sheystore.data.repository.cart.ICartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class CartModule {

    @Binds
    abstract fun provideCartRepository(cartRepositoryImp: CartRepositoryImp) : ICartRepository
}