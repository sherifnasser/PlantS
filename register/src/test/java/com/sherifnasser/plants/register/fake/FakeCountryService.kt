package com.sherifnasser.plants.register.fake

import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.domain.model.Country

class FakeCountryService:CountryService{

    private val countries=emptyList<Country>()

    override fun getAllCountries(): List<Country> {
        return countries
    }

    override fun getCountryByCallingCode(callingCode: Int): Country {
        return Country(
            name = "Name of $callingCode",
            isoName = "ZZ",
            callingCode = callingCode
        )
    }

    override fun getCountryByIsoName(isoName: String): Country {
        return Country(
            name = "Name of $isoName",
            isoName = isoName,
            callingCode = -1
        )
    }
}