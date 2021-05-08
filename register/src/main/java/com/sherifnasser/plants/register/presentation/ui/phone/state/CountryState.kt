package com.sherifnasser.plants.register.presentation.ui.phone.state

import com.sherifnasser.plants.register.domain.model.Country

sealed class CountryState {
    object Unselected: CountryState()
    data class Selected(val country: Country) : CountryState()
    object Unknown: CountryState()
}