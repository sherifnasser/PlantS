package com.sherifnasser.plants.register.data.implementation

import android.telephony.TelephonyManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.sherifnasser.plants.register.data.abstraction.PhoneNumberService
import com.sherifnasser.plants.register.domain.model.Country
import java.util.Locale
import javax.inject.Inject

class PhoneNumberService_Impl
@Inject
constructor(
    private val phoneNumberUtil: PhoneNumberUtil,
    private val telephonyManager: TelephonyManager
):PhoneNumberService{

    override fun getSimCountryIsoName(): String {
        return telephonyManager.simCountryIso.toUpperCase(Locale.ROOT)
    }

    override fun formatPhoneNumber(e164Number: String, countryIsoName:String): String {
        val formatter=phoneNumberUtil.getAsYouTypeFormatter(countryIsoName)
        lateinit var number:String
        e164Number.forEach { c->
            number=formatter.inputDigit(c)
        }
        return number
    }
}