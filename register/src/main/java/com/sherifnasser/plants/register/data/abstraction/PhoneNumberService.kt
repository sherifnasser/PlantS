package com.sherifnasser.plants.register.data.abstraction

interface PhoneNumberService {

    fun getSimCountryIsoName():String

    fun formatPhoneNumber(e164Number:String, countryIsoName:String):String
}