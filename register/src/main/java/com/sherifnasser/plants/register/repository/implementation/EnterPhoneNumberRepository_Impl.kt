package com.sherifnasser.plants.register.repository.implementation

import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.data.abstraction.PhoneNumberService
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository
import javax.inject.Inject

class EnterPhoneNumberRepository_Impl
@Inject
constructor(
    private val countryService: CountryService,
    private val phoneNumberService: PhoneNumberService
):EnterPhoneNumberRepository{

    override fun getAllCountries(): List<Country> {
        return countryService.getAllCountries()
    }

    override fun getSimCountry(): Country {
        val simIsoName=phoneNumberService.getSimCountryIsoName()
        return countryService.getCountryByIsoName(simIsoName)
    }

    override fun getCountryByCallingCode(callingCode: Int): Country {
        return countryService.getCountryByCallingCode(callingCode)
    }

    override fun formatPhoneNumber(e164Number: String, countryIsoName: String): String {
        return phoneNumberService.formatPhoneNumber(e164Number, countryIsoName)
    }
}