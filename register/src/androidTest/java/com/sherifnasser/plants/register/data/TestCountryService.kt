package com.sherifnasser.plants.register.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.di.PhoneNumberModule
import com.sherifnasser.plants.register.di.SystemLocale
import com.sherifnasser.plants.register.domain.model.Country
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PhoneNumberModule::class)
@RunWith(AndroidJUnit4::class)
class TestCountryService {

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

    @BindValue
    @SystemLocale
    val systemLocale=Locale("AR")

    @Inject
    lateinit var countryService: CountryService

    private val expectedCountry by lazy{
        Country(
            name = "مصر",
            isoName = "EG",
            callingCode = 20
        )
    }

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun testGetCountryByCallingCode_returnCorrectCountryInArabic(){
        val actualCountry=countryService
            .getCountryByCallingCode(callingCode = expectedCountry.callingCode)

        assertThat(actualCountry).isEqualTo(expectedCountry)
    }

    @Test
    fun testGetCountryByIsoName_returnCorrectCountryInArabic(){
        val actualCountry=countryService
            .getCountryByIsoName(isoName = expectedCountry.isoName)

        assertThat(actualCountry).isEqualTo(expectedCountry)
    }
}