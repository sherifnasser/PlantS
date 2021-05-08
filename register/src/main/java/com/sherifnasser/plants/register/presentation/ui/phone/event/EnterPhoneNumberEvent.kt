package com.sherifnasser.plants.register.presentation.ui.phone.event

sealed class EnterPhoneNumberEvent {
    object DisplayCountriesDialog: EnterPhoneNumberEvent()
    object CloseCountriesDialog: EnterPhoneNumberEvent()
    data class EnterCountryCode(val code:String): EnterPhoneNumberEvent()
    data class EnterNumber(val number: String): EnterPhoneNumberEvent()
}