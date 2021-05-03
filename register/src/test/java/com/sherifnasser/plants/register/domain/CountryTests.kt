package com.sherifnasser.plants.register.domain

import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.domain.util.CountryIsoNameException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CountryTests {

    @Nested
    @DisplayName("When instantiating a country")
    inner class WhenInstantiatingCountry{

        @DisplayName("Iso name is 0 chars at length throws a CountryIsoNameException")
        @Test
        fun isoNameLess0CharLength(){

            val e=assertThrows(CountryIsoNameException::class.java) {
                Country(
                    name = "Egypt",
                    isoName = "",
                    callingCode = 20
                )
            }

            assertThat(e).hasMessageThat().contains("Egypt")
            assertThat(e).hasMessageThat().contains("\"\"")
            assertThat(e).hasMessageThat().contains(
                CountryIsoNameException.Cause.Length2Chars.message
            )
        }

        @DisplayName("Iso name is 1 char at length throws a CountryIsoNameException")
        @Test
        fun isoNameLess1CharLength(){

            val e=assertThrows(CountryIsoNameException::class.java) {
                Country(
                    name = "Egypt",
                    isoName = "e",
                    callingCode = 20
                )
            }

            assertThat(e).hasMessageThat().contains("Egypt")
            assertThat(e).hasMessageThat().contains("\"e\"")
            assertThat(e).hasMessageThat().contains(
                CountryIsoNameException.Cause.Length2Chars.message
            )
        }

        @DisplayName("Iso name is greater than 2 chars throws a CountryIsoNameException")
        @Test
        fun isoNameGreaterThan2CharsLength(){

            val e=assertThrows(CountryIsoNameException::class.java) {
                Country(
                    name = "Egypt",
                    isoName = "egy",
                    callingCode = 20
                )
            }

            assertThat(e).hasMessageThat().contains("Egypt")
            assertThat(e).hasMessageThat().contains("\"egy\"")
            assertThat(e).hasMessageThat().contains(
                CountryIsoNameException.Cause.Length2Chars.message
            )
        }

        @DisplayName("With lower case isoName throws a CountryIsoNameException")
        @Test
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