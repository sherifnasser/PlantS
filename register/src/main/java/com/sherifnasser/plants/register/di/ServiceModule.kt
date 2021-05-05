package com.sherifnasser.plants.register.di

import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.data.implementation.CountryService_Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindCountryService(
        service:CountryService_Impl
    ):CountryService


}