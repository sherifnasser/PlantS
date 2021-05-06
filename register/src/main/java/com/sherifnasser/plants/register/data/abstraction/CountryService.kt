package com.sherifnasser.plants.register.data.abstraction

import com.sherifnasser.plants.register.domain.model.Country

interface CountryService {

    fun getAllCountries():List<Country>

    fun getCountryByCallingCode(callingCode:Int):Country

    fun getCountryByIsoName(isoName:String):Country

}