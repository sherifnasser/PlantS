package com.sherifnasser.plants.register.domain.model

import com.sherifnasser.plants.register.domain.util.CountryIsoNameException

data class Country(
    val name:String,
    val isoName:String,
    val callingCode:Int
){
    init {
        if(isoName.length!=2)
            throw CountryIsoNameException(
                countryName = name,
                countryIsoName = isoName,
                cause = CountryIsoNameException.Cause.Length2Chars
            )
        isoName.forEach { c->
            if (!c.isUpperCase())
                throw CountryIsoNameException(
                    countryName = name,
                    countryIsoName = isoName,
                    cause = CountryIsoNameException.Cause.AllUpperCase
                )
        }
    }
}