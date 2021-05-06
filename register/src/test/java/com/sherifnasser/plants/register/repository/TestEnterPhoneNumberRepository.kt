package com.sherifnasser.plants.register.repository

import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.data.abstraction.PhoneNumberService
import com.sherifnasser.plants.register.fake.FakeCountryService
import com.sherifnasser.plants.register.fake.FakePhoneNumberService
import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository
import com.sherifnasser.plants.register.repository.implementation.EnterPhoneNumberRepository_Impl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestEnterPhoneNumberRepository {
    private lateinit var countryService: CountryService
    private lateinit var phoneNumberService: PhoneNumberService
    private lateinit var enterPhoneNumberRepository: EnterPhoneNumberRepository

    private val simCountryIsoName by lazy { "EG" }

    @BeforeEach
    fun setUp(){
        countryService=FakeCountryService()
        phoneNumberService=FakePhoneNumberService(
            simCountryIsoName = simCountryIsoName
        )
        enterPhoneNumberRepository=EnterPhoneNumberRepository_Impl(
            countryService, phoneNumberService
        )
    }

    @Test
    fun testGetAllCountries(){
        val actual=enterPhoneNumberRepository.getAllCountries()
        val expected=countryService.getAllCountries()
        assertThat(actual).isSameInstanceAs(expected)
    }


    @Test
    fun testGetSimCountry(){
        val actual=enterPhoneNumberRepository.getSimCountry()
        val expected=countryService.getCountryByIsoName(simCountryIsoName)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testGetCountryByCallingCode(){
        val actual=enterPhoneNumberRepository.getCountryByCallingCode(20)
        val expected=countryService.getCountryByCallingCode(20)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testFormatPhoneNumber(){
        val phoneNumber="+201234567899"
        val actual=enterPhoneNumberRepository.formatPhoneNumber(
            e164Number = phoneNumber,
            countryIsoName = simCountryIsoName
        )
        val expected=phoneNumberService.formatPhoneNumber(
            e164Number = phoneNumber,
            countryIsoName = simCountryIsoName
        )
        assertThat(actual).isEqualTo(expected)
    }
}