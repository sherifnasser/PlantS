package com.sherifnasser.plants.register.fake

import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.NoSimCardException
import com.sherifnasser.plants.register.domain.util.UnknownCountryException
import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository

class FakeEnterPhoneNumberRepository(
    var isSimCardAvailable:Boolean=true
):EnterPhoneNumberRepository{

    companion object{
        val PALESTINE by lazy{Country("Palestine", "PS", 970)}
        val EGYPT by lazy{Country("Egypt", "EG", 20)}
        val SAUDI_ARABIA by lazy{Country("Saudi Arabia", "SA", 966)}
        val TURKEY by lazy{Country("Turkey", "TR", 90)}
    }

    private val _simCountry by lazy{EGYPT}

    private val _allCountries by lazy{
        listOf(
            PALESTINE,
            EGYPT,
            SAUDI_ARABIA,
            TURKEY
        )
    }

    /**
     * To add space to number after index to make formatting process more simple
     * For example if number is +201234567899 for [EGYPT] it will be formatted to +20 12 34 56 78 99
     * And if number is +901234567899 for [TURKEY] it will be formatted to +90 1234 5678 99
     */
    private val _allCountriesSpaceFormattingIndex by lazy{
        mapOf(
            PALESTINE to 1,
            EGYPT to 2,
            SAUDI_ARABIA to 3,
            TURKEY to 4
        )
    }

    override fun getAllCountries():List<Country>{
        return _allCountries
    }

    override fun getSimCountry():Country{
        return if(isSimCardAvailable) _simCountry
        else throw NoSimCardException()
    }

    override fun getCountryByCallingCode(callingCode:Int):Country{
        _allCountries.forEach{
            if(it.callingCode==callingCode)
                return it
        }
        throw UnknownCountryException(callingCode=callingCode)
    }

    /**
     * Find country in [_allCountries] by [countryIsoName] and remove prefix "+countryCode" from [e164Number]
     * and store it in phoneNumberWithoutCode then iterate phoneNumberWithoutCode and if the next char is a digit
     * add it to formattedNumberWithoutCode and add space if length can be divided over [_allCountriesSpaceFormattingIndex] of country
     *
     * For example: if [e164Number] is +201234-567899 and [countryIsoName] is EG for [EGYPT]
     * the phoneNumberWithoutCode will be 1234-567899 and we will add only digits to formattedNumberWithoutCode
     * and add space every [_allCountriesSpaceFormattingIndex] for [EGYPT]
     * since [_allCountriesSpaceFormattingIndex] for [EGYPT] is 2 we will add space to it every two digits
     * so formattedNumberWithoutCode will be 12 34 56 78 99
     * finally it will return formattedNumber that equals to (code + formattedNumberWithoutCode)
     * so full formattedNumber will be +20 12 34 56 78 99
     */
    override fun formatPhoneNumber(e164Number:String,countryIsoName:String):String{
        var formattedNumber=""
        var formattedNumberWithoutCode=""
        _allCountries.forEach{
            if (it.isoName==countryIsoName){
                val code="+${it.callingCode}"
                val phoneNumberWithoutCode=e164Number.removePrefix(code)
                phoneNumberWithoutCode.forEach{c->
                    if (c.isDigit()){
                        formattedNumberWithoutCode+=c
                        val l=formattedNumberWithoutCode.length
                        val i=_allCountriesSpaceFormattingIndex[it]!!
                        if(l%i==0)formattedNumberWithoutCode+=" "
                    }
                }

                formattedNumber="$code $formattedNumberWithoutCode"
            }
        }
        return formattedNumber
    }
}