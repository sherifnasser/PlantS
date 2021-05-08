package com.sherifnasser.plants.register.presentation.ui.phone

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import com.sherifnasser.plants.register.domain.util.NoSimCardException
import com.sherifnasser.plants.register.domain.util.UnknownCountryException
import com.sherifnasser.plants.register.presentation.ui.phone.event.EnterPhoneNumberEvent
import com.sherifnasser.plants.register.presentation.ui.phone.state.CountryState
import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterPhoneNumberViewModel
@Inject
constructor(private val repository: EnterPhoneNumberRepository):ViewModel(){

    private val _countryState by lazy { mutableStateOf<CountryState>(CountryState.Unknown) }
    private val _countryCodeText by lazy{ mutableStateOf("0") }
    private val _phoneNumberText by lazy{ mutableStateOf("") }
    private val _phoneNumberFocusRequester by lazy{ mutableStateOf(FocusRequester()) }
    private val _isCountriesDialogShown by lazy{ mutableStateOf(false) }

    val countryState:State<CountryState> get() = _countryState
    val countryCodeText:State<String> get() = _countryCodeText
    val phoneNumberText:State<String> get() = _phoneNumberText
    val phoneNumberFocusRequester:State<FocusRequester> get() = _phoneNumberFocusRequester
    val isCountriesDialogShown:State<Boolean> get() = _isCountriesDialogShown

    init {
        try {
            val simCountry=repository.getSimCountry()
            _countryState.value=CountryState.Selected(simCountry)
            _countryCodeText.value=simCountry.callingCode.toString()
        }catch (e:NoSimCardException){
            /*
                There are initial values to countryFieldState and countryCodeText,
                so we don't handle any thing here
             */
        }
    }

    fun onTriggerEvent(event: EnterPhoneNumberEvent){
        when(event){
            is EnterPhoneNumberEvent.EnterCountryCode -> handleEnterCountryCodeEvent(event.code)
            is EnterPhoneNumberEvent.EnterNumber -> handleEnterNumberEvent(event.number)
            EnterPhoneNumberEvent.DisplayCountriesDialog -> handleDisplayCountriesDialogEvent()
            EnterPhoneNumberEvent.CloseCountriesDialog -> handleCloseCountriesDialogEvent()
        }
    }

    private fun handleEnterCountryCodeEvent(callingCode:String){
        if(callingCode.length<=3){
            _countryCodeText.value=callingCode
            if(callingCode.isEmpty())
                _countryState.value=CountryState.Unselected
            else{
                try {
                    val newCountry=repository.getCountryByCallingCode(callingCode=callingCode.toInt())
                    _countryState.value=CountryState.Selected(newCountry)
                    _phoneNumberFocusRequester.value.requestFocus()
                    formatNumberField()
                }catch (e:UnknownCountryException){
                    _countryState.value=CountryState.Unknown
                }
            }
        }
    }

    private fun handleEnterNumberEvent(number: String){
        formatNumberField(newNumber = number)
    }

    private fun handleDisplayCountriesDialogEvent(){
        _isCountriesDialogShown.value=true
    }

    private fun handleCloseCountriesDialogEvent(){
        _isCountriesDialogShown.value=false
    }

    private fun formatNumberField(newNumber:String=_phoneNumberText.value){
        val formattedNumberWithoutCode= when (val countryState=countryState.value) {
            is CountryState.Selected -> {
                val callingCode=_countryCodeText.value
                val formattedNumber=repository.formatPhoneNumber(
                    e164Number = "+$callingCode$newNumber",
                    countryIsoName = countryState.country.isoName
                )
                formattedNumber.removePrefix("+$callingCode").trim()
            }
            else -> newNumber
        }
        _phoneNumberText.value=formattedNumberWithoutCode
    }
}