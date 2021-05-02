package com.sherifnasser.plants.register.di

import android.content.Context
import android.telephony.TelephonyManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RegisterAppModule {

    @Provides
    fun providePhoneNumberUtil(): PhoneNumberUtil = PhoneNumberUtil.getInstance()

    @Provides
    fun provideTelephonyManager(@ApplicationContext ctx: Context): TelephonyManager =
        ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
}