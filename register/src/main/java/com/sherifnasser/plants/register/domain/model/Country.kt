package com.sherifnasser.plants.register.domain.model

import com.sherifnasser.plants.register.domain.util.CountryIsoNameException

data class Country(
    val name:String,
    val isoName:String,
    val callingCode:Int
){
    init {
        isoName.forEach { c->
            if (!c.isUpperCase())
                throw CountryIsoNameException(
                    countryName = name,
                    countryIsoName = isoName
                )
        }
    }
}