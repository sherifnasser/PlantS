package com.sherifnasser.plants.register.data.implementation

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.di.SystemLocale
import com.sherifnasser.plants.register.domain.model.Country
import java.util.*
import javax.inject.Inject

class CountrySevice_Impl
@Inject
constructor(
    private val phoneNumberUtil: PhoneNumberUtil,
    @SystemLocale private val systemLocal:Locale
):CountryService{

    override fun getSimCountry(): Country {
        TODO("Not yet implemented")
    }

    override fun getCountryByCallingCode(callingCode: Int): Country {
        val isoName=phoneNumberUtil.getRegionCodeForCountryCode(callingCode)
        val name=getCountryDisplayName(isoName = isoName)
        return Country(
            callingCode = callingCode,
            isoName = isoName,
            name = name
        )
    }

    override fun getCountryByIsoName(isoName: String): Country {
        val callingCode=phoneNumberUtil.getCountryCodeForRegion(isoName)
        val name=getCountryDisplayName(isoName = isoName)
        return Country(
            callingCode = callingCode,
            isoName = isoName,
            name = name
        )
    }

    private fun getCountryDisplayName(isoName: String):String{
        return Locale("",isoName).getDisplayCountry(systemLocal)
    }
}