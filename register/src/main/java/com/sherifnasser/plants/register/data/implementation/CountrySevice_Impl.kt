package com.sherifnasser.plants.register.data.implementation

import android.telephony.TelephonyManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.di.SystemLocale
import com.sherifnasser.plants.register.domain.model.Country
import java.util.Locale
import javax.inject.Inject

class CountrySevice_Impl
@Inject
constructor(
    private val telephonyManager: TelephonyManager,
    private val phoneNumberUtil: PhoneNumberUtil,
    @SystemLocale private val systemLocal:Locale
):CountryService{

    override fun getSimCountry(): Country {
        val isoName=telephonyManager.simCountryIso.toUpperCase(Locale.ROOT)
        val callingCode=getCallingCode(isoName = isoName)
        val name=getDisplayName(isoName = isoName)
        return Country(
            name = name,
            isoName = isoName,
            callingCode = callingCode
        )
    }

    override fun getCountryByCallingCode(callingCode: Int): Country {
        val isoName=getIsoName(callingCode = callingCode)
        val name=getDisplayName(isoName = isoName)
        return Country(
            name = name,
            isoName = isoName,
            callingCode = callingCode
        )
    }

    override fun getCountryByIsoName(isoName: String): Country {
        val callingCode=getCallingCode(isoName = isoName)
        val name=getDisplayName(isoName = isoName)
        return Country(
            name = name,
            isoName = isoName,
            callingCode = callingCode
        )
    }





    private fun getCallingCode(isoName: String):Int{
        return phoneNumberUtil.getCountryCodeForRegion(isoName.toUpperCase(Locale.ROOT))
    }

    private fun getIsoName(callingCode: Int):String{
        return phoneNumberUtil.getRegionCodeForCountryCode(callingCode)
    }

    private fun getDisplayName(isoName: String):String{
        return Locale("",isoName.toUpperCase(Locale.ROOT)).getDisplayCountry(systemLocal)
    }
}