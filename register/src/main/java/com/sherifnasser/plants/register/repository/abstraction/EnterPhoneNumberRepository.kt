package com.sherifnasser.plants.register.repository.abstraction

import com.sherifnasser.plants.register.domain.model.Country

interface EnterPhoneNumberRepository {

    fun getAllCountries():List<Country>

    fun getSimCountry():Country

    fun getCountryByCallingCode(callingCode:Int):Country

    fun formatPhoneNumber(e164Number:String,countryIsoName:String):String
}