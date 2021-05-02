package com.sherifnasser.plants.register.domain.util

class CountryIsoNameException(countryName:String,countryIsoName:String):IllegalArgumentException(
    "Cannot instantiate country "+
    "as the isoName of $countryName \"$countryIsoName\" " +
    "should be all upper case"
)