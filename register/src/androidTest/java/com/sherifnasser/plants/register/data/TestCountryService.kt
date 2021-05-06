package com.sherifnasser.plants.register.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.core.util.setSystemLocaleForTest
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.UnknownCountryException
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TestCountryService {

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

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
        setSystemLocaleForTest(Locale("AR"))
        hiltRule.inject()
    }

    @Test
    fun testGetCountryByCallingCode_codeIs20AndSystemLocaleIsArabic_returnEgyptInArabic(){
        val actualCountry=countryService
            .getCountryByCallingCode(callingCode = expectedCountry.callingCode)

        assertThat(actualCountry).isEqualTo(expectedCountry)
    }

    @Test
    fun testGetCountryByIsoName_isoIsEGAndSystemLocaleIsArabic_returnEgyptInArabic(){
        val actualCountry=countryService
            .getCountryByIsoName(isoName = expectedCountry.isoName)

        assertThat(actualCountry).isEqualTo(expectedCountry)
    }

    @Test
    fun testGetCountryByCallingCode_wrongCode_throwsUnknownCountryException(){

        val e=assertThrows(UnknownCountryException::class.java){
            countryService.getCountryByCallingCode(callingCode = -111)
        }

        assertThat(e).hasMessageThat().contains("+-111")
    }

    @Test
    fun testGetCountryByIsoName_wrongName_throwsUnknownCountryException(){

        val e=assertThrows(UnknownCountryException::class.java){
            countryService.getCountryByIsoName(isoName = "ZZ")
        }

        assertThat(e).hasMessageThat().contains("ZZ")
    }
}