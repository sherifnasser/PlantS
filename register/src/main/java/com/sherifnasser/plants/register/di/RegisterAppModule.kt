package com.sherifnasser.plants.register.di

import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterAppModule {

    @Provides
    fun providePhoneNumberUtil(): PhoneNumberUtil = PhoneNumberUtil.getInstance()
}