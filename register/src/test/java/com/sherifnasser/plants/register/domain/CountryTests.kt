package com.sherifnasser.plants.register.domain

import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.CountryIsoNameException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CountryTests {

    @DisplayName("Instantiating country with lower case isoName throws a CountryIsoNameException")
    @Test
    fun instantiateCountry(){

        val e=assertThrows(CountryIsoNameException::class.java) {
            Country(
                name = "Egypt",
                isoName = "eg",
                callingCode = 20
            )
        }

        assertThat(e).isInstanceOf(IllegalArgumentException::class.java)
        assertThat(e).hasMessageThat().contains("Egypt")
        assertThat(e).hasMessageThat().contains("eg")
    }
}