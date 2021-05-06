package com.sherifnasser.plants.register.fake

import com.sherifnasser.plants.register.data.abstraction.PhoneNumberService

class FakePhoneNumberService(
    private val simCountryIsoName:String
):PhoneNumberService {

    override fun getSimCountryIsoName(): String {
        return simCountryIsoName
    }

    /**
     * combine [e164Number] and [countryIsoName]
     * to test if correct params are passed from repo layer or not
     */
    override fun formatPhoneNumber(e164Number: String, countryIsoName: String): String {
        return e164Number+countryIsoName
    }

}