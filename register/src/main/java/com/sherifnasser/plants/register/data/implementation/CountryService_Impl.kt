package com.sherifnasser.plants.register.data.implementation

import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.sherifnasser.plants.register.data.abstraction.CountryService
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.UnknownCountryException
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class CountryService_Impl
@Inject
constructor(
    @ApplicationContext private val context: Context,
    private val countriesMap: Map<String,Int>
):CountryService{

    private val systemLocale get() = ConfigurationCompat.getLocales(context.resources.configuration)[0]

    private val countries by lazy {
        countriesMap.entries.map { e->
            val isoName=e.key
            val callingCode=e.value
            Country(
                name = getDisplayName(isoName = isoName),
                isoName = isoName,
                callingCode = callingCode
            )
        }.sortedBy { it.name }
    }

    override fun getAllCountries(): List<Country> = countries

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
        return countriesMap[isoName]?: throw UnknownCountryException(isoName=isoName)
    }

    private fun getIsoName(callingCode: Int):String{
        countriesMap.entries.forEach { e->
            if(e.value==callingCode)
                return e.key
        }
        throw UnknownCountryException(callingCode=callingCode)
    }

    private fun getDisplayName(isoName: String):String{
        if(countriesMap[isoName]==null)
            throw UnknownCountryException(isoName = isoName)

        val countryLocale=Locale("",isoName.toUpperCase(Locale.ROOT))

        return countryLocale.getDisplayCountry(systemLocale)
    }
}