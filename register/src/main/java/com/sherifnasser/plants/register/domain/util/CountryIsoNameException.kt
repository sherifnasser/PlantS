package com.sherifnasser.plants.register.domain.util

class CountryIsoNameException(
    countryName:String,
    countryIsoName:String,
    cause:Cause
):IllegalArgumentException(
    "Cannot instantiate country "+
    "as the isoName of $countryName \"$countryIsoName\" "+
    cause.message
){
    sealed class Cause(val message:String){
        object AllUpperCase:Cause("should be all upper case")
        object Length2Chars:Cause("should be 2 characters at length")
    }
}