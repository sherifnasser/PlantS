package com.sherifnasser.plants.register.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
object PhoneNumberModule {

    @SystemLocale
    @Provides
    fun provideSystemLocale(): Locale = Locale.getDefault()
}