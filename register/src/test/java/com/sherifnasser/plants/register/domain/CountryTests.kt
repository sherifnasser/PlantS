package com.sherifnasser.plants.register.domain

import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.CountryIsoNameException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.junit.jupiter.params.provider.ValueSource

class CountryTests {

    @Nested
    @DisplayName("When instantiating a country")
    inner class WhenInstantiatingCountry{

        @ParameterizedTest
        @EmptySource        // 0 length
        @ValueSource(strings=[
            "e",            // 1 length
            "e ",           // 2 length but has space
            "  ",           // 2 length but blank
            "egy",          // greater than 2
            "egypt",        // greater than 2
            "EGY",          // greater than 2 all upper case, to test checking for length before all upper case
        ])
        @DisplayName("Iso name isn't 2 chars at length or it has space throws a CountryIsoNameException")
        fun isoNameCharactersLength(isoName:String){

            val e=assertThrows(CountryIsoNameException::class.java) {
                Country(
                    name = "Egypt",
                    isoName = isoName,
                    callingCode = 20
                )
            }

            assertThat(e).hasMessageThat().contains("Egypt")
            assertThat(e).hasMessageThat().contains("\"$isoName\"")
            assertThat(e).hasMessageThat().contains(
                CountryIsoNameException.Cause.Length2Chars.message
            )
        }


        @Test
        @DisplayName("Iso name is lower case throws a CountryIsoNameException")
        fun isoNameAllUpperCase(){

            val e=assertThrows(CountryIsoNameException::class.java) {
                Country(
                    name = "Egypt",
                    isoName = "eg",
                    callingCode = 20
                )
            }

            assertThat(e).hasMessageThat().contains("Egypt")
            assertThat(e).hasMessageThat().contains("\"eg\"")
            assertThat(e).hasMessageThat().contains(
                CountryIsoNameException.Cause.AllUpperCase.message
            )
        }
    }
}