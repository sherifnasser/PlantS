package com.sherifnasser.plants.register.presentation.ui.phone

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import com.sherifnasser.plants.register.domain.model.Country
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
    private val _dialogQueryText by lazy{ mutableStateOf("") }
    private val _allCountries by lazy{ mutableStateOf(repository.getAllCountries()) }
    private val _countriesInDialog by lazy{ mutableStateOf(_allCountries.value) }

    val countryState:State<CountryState> get() = _countryState
    val countryCodeText:State<String> get() = _countryCodeText
    val phoneNumberText:State<String> get() = _phoneNumberText
    val phoneNumberFocusRequester:State<FocusRequester> get() = _phoneNumberFocusRequester
    val isCountriesDialogShown:State<Boolean> get() = _isCountriesDialogShown
    val dialogQueryText:State<String> get() = _dialogQueryText
    val countriesInDialog:State<List<Country>> get() = _countriesInDialog


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
            is EnterPhoneNumberEvent.OnEnterCountryCode -> handleOnEnterCountryCodeEvent(event.code)
            is EnterPhoneNumberEvent.OnEnterNumber -> handleOnEnterNumberEvent(event.number)
            EnterPhoneNumberEvent.DisplayCountriesDialog -> handleDisplayCountriesDialogEvent()
            is EnterPhoneNumberEvent.OnCountriesDialogQueryChange -> handleOnCountriesDialogQueryChangeEvent(event.query)
            is EnterPhoneNumberEvent.OnCountrySelectedFromDialog -> handleOnCountrySelectedFromDialogEvent(event.country)
            EnterPhoneNumberEvent.CloseCountriesDialog -> handleCloseCountriesDialogEvent()
        }
    }

    private fun handleOnEnterCountryCodeEvent(callingCode:String){
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

    private fun handleOnEnterNumberEvent(number: String){
        formatNumberField(newNumber = number)
    }

    private fun handleDisplayCountriesDialogEvent(){
        _isCountriesDialogShown.value=true
    }

    private fun handleCloseCountriesDialogEvent(){
        _isCountriesDialogShown.value=false
        _dialogQueryText.value=""
    }

    private fun handleOnCountriesDialogQueryChangeEvent(query:String){
        _dialogQueryText.value=query
        val allCountries=_allCountries.value
        val countriesQuery=query.trim()

        _countriesInDialog.value=
            if(countriesQuery.isEmpty())allCountries
            else allCountries.filter {
                it.name.startsWith(
                    prefix = query,
                    ignoreCase = true
                )
            }
    }

    private fun handleOnCountrySelectedFromDialogEvent(country: Country){
        _countryState.value=CountryState.Selected(country)
        _countryCodeText.value=country.callingCode.toString()
        _phoneNumberFocusRequester.value.requestFocus()
        formatNumberField()
        _dialogQueryText.value=""
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