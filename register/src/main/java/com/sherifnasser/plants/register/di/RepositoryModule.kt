package com.sherifnasser.plants.register.di

import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository
import com.sherifnasser.plants.register.repository.implementation.EnterPhoneNumberRepository_Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEnterPhoneNumberRepository(
        repository: EnterPhoneNumberRepository_Impl
    ):EnterPhoneNumberRepository

}