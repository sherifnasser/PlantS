package com.sherifnasser.plants.register.presentation.ui.phone.event

import com.sherifnasser.plants.register.domain.model.Country

sealed class EnterPhoneNumberEvent {
    object DisplayCountriesDialog: EnterPhoneNumberEvent()
    data class OnCountriesDialogQueryChange(val query: String): EnterPhoneNumberEvent()
    data class OnCountrySelectedFromDialog(val country: Country): EnterPhoneNumberEvent()
    object CloseCountriesDialog: EnterPhoneNumberEvent()
    data class OnEnterCountryCode(val code:String): EnterPhoneNumberEvent()
    data class OnEnterNumber(val number: String): EnterPhoneNumberEvent()
}