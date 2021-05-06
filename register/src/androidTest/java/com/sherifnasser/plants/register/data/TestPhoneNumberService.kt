package com.sherifnasser.plants.register.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.data.abstraction.PhoneNumberService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TestPhoneNumberService {

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

    @Inject
    lateinit var phoneNumberService: PhoneNumberService

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    private fun formatPhoneNumber(e164Number:String)=
        phoneNumberService.formatPhoneNumber(
            e164Number=e164Number,
            countryIsoName="EG"
        )

    @Test
    fun testGetSimCountry_egyptianSimCard_returnEG(){
        val isoName=phoneNumberService.getSimCountryIsoName()

        assertThat(isoName).isEqualTo("EG")
    }

    @Test
    fun testFormatNumberToInternationalForm_fullValidNumber_formatNumberCorrectly(){

        assertThat(formatPhoneNumber(e164Number = "+201234567899"))
            .isEqualTo("+20 123 456 7899")

    }

    @Test
    fun testFormatNumberToInternationalForm_formattedNumber_formatNumberCorrectly(){

        assertThat(formatPhoneNumber(e164Number = "+20 123 456 7899"))
            .isEqualTo("+20 123 456 7899")

    }

    @Test
    fun testFormatNumberToInternationalForm_userIsStillWritingNumber_formatNumberCorrectly(){

        assertThat(formatPhoneNumber(e164Number = "+2012345678"))
            .isEqualTo("+20 123 456 78")

    }

    @Test
    fun testFormatNumberToInternationalForm_numberIsLong_doesNotFormatNumber(){

        assertThat(formatPhoneNumber(e164Number = "+2012345678991212"))
            .isEqualTo("+2012345678991212")

    }
}