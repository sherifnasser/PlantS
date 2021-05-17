package com.sherifnasser.plants.register.presentation.ui.phone

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sherifnasser.plants.core.util.onNodeWithContentDescription
import com.sherifnasser.plants.core.util.stringResource
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.RegisterHiltTestActivity
import com.sherifnasser.plants.register.di.RepositoryModule
import com.sherifnasser.plants.register.domain.model.Country
import com.sherifnasser.plants.register.fake.FakeEnterPhoneNumberRepository
import com.sherifnasser.plants.register.fake.FakeEnterPhoneNumberRepository.Companion.EGYPT
import com.sherifnasser.plants.register.fake.FakeEnterPhoneNumberRepository.Companion.PALESTINE
import com.sherifnasser.plants.register.fake.FakeEnterPhoneNumberRepository.Companion.SAUDI_ARABIA
import com.sherifnasser.plants.register.repository.abstraction.EnterPhoneNumberRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
@RunWith(AndroidJUnit4::class)
class TestEnterPhoneNumberScreen{

    @Suppress("LeakingThis")
    @get:Rule(order=0)
    var hiltRule=HiltAndroidRule(this)

    @get:Rule(order=1)
    val composeTestRule=createAndroidComposeRule<RegisterHiltTestActivity>()

    private lateinit var vm:EnterPhoneNumberViewModel

    @BindValue
    val repository:EnterPhoneNumberRepository=FakeEnterPhoneNumberRepository()

    private val repo get()=repository as FakeEnterPhoneNumberRepository

    private fun setContent(){
        composeTestRule.setContent{
            vm=viewModel()
            EnterPhoneNumberScreen(viewModel=vm)
        }
    }

    private fun onCountryField()=
        composeTestRule.onNodeWithContentDescription(
            labelResId = R.string.cd_country_field_in_enterPhoneNumberScreen
        )

    private fun onCountryCodeField()=
        composeTestRule.onNodeWithContentDescription(
            labelResId = R.string.cd_country_code_field_in_enterPhoneNumberScreen
        )

    private fun onPhoneNumberField()=
        composeTestRule.onNodeWithContentDescription(
            labelResId = R.string.cd_phone_number_field_in_enterPhoneNumberScreen
        )

    private fun onCountriesDialog()=
        composeTestRule.onNodeWithContentDescription(
            labelResId = R.string.cd_countries_dialog_in_enterPhoneNumberScreen
        )
    
    private fun onCountriesDialogQueryField()=
        composeTestRule.onNodeWithContentDescription(
            labelResId = R.string.cd_query_field_in_countries_dialog_in_enterPhoneNumberScreen
        )

    private fun onCountryOnDialog(country:Country)=
        composeTestRule.onNodeWithContentDescription("${country.name},+${country.callingCode}")

    private fun assertCountryIsDisplayedOnDialog(country:Country){
        onCountryOnDialog(country).assertExists()

        onCountriesDialog().assert(
            hasAnyChild(
                hasText(country.name)
            )
        )
        onCountriesDialog().assert(
            hasAnyChild(
                hasText("+${country.callingCode}")
            )
        )
    }

    private val unknownCountryMessage get()=composeTestRule.stringResource(id=R.string.unknown_country)

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun testDisplayedCountry_simCardIsAvailable_displayedCountryIsSameAsSimCardCountry(){
        val simCountry=repo.getSimCountry()

        setContent()

        onCountryField().assert(hasText(simCountry.name))
        onCountryCodeField().assert(hasText(simCountry.callingCode.toString()))
    }

    @Test
    fun testDisplayedCountry_simCardIsNotAvailable_unknownCountryMessageIsDisplayed(){

        repo.isSimCardAvailable=false
        setContent()

        onCountryField().assert(hasText(unknownCountryMessage))
        onCountryCodeField().assert(hasText("0"))
    }

    @Test
    fun testOnTriggerEnterCountryCodeEvent_clearText_selectYourCountryMessageIsDisplayed(){
        val selectYourCountryMessage=composeTestRule.stringResource(id=R.string.select_your_country)

        setContent()
        onCountryCodeField().performTextClearance()

        onCountryCodeField().assert(hasText(""))
        onCountryField().assert(hasText(selectYourCountryMessage))
        onPhoneNumberField().assertIsNotFocused()
    }

    @Test
    fun testOnTriggerEnterCountryCodeEvent_enterInvalidCountryCode_unknownCountryMessageIsDisplayed(){
        setContent()
        onCountryCodeField().performTextReplacement("200")

        onCountryCodeField().assert(hasText("200"))
        onCountryField().assert(hasText(unknownCountryMessage))
        onPhoneNumberField().assertIsNotFocused()
    }

    @Test
    fun testOnTriggerEnterCountryCodeEvent_enterInvalidCharacter_characterIsNotEntered(){

        setContent()
        onCountryCodeField().performTextReplacement("20")
        onCountryCodeField().performTextInput("+-./$")
        onCountryCodeField().performTextInput("0")

        onCountryCodeField().assert(hasText("200"))
        onCountryField().assert(hasText(unknownCountryMessage))
        onPhoneNumberField().assertIsNotFocused()
    }

    @Test
    fun testOnTriggerEnterCountryCodeEvent_enterCodeWithMoreThan3Chars_shouldAcceptOnly3Chars(){

        setContent()
        onCountryCodeField().performTextInput("0123")

        onCountryCodeField().assert(hasText("200"))
    }

    @Test
    fun testOnTriggerEnterCountryCodeEvent_enterValidCountryCode_countryFieldValueShouldChangeToCountryNameAndPhoneNumberIsFocused(){
        val code=PALESTINE.callingCode.toString()

        setContent()
        onCountryCodeField().performTextReplacement(code)

        onCountryCodeField().assert(hasText(code))
        onCountryField().assert(hasText(PALESTINE.name))
        onPhoneNumberField().assertIsFocused()
    }

    @Test
    fun testCountryCodeField_performImeAction_phoneNumberFieldIsFocused(){

        setContent()
        onCountryCodeField().performImeAction()

        onPhoneNumberField().assertIsFocused()
    }

    @Test
    fun testOnTriggerEnterPhoneNumberEvent_unformattedNumberAndValidCountry_shouldFormatNumber(){

        val phoneNumberWithoutCode="1234567899"
        val code="+${repo.getSimCountry().callingCode}"
        val e164number="$code$phoneNumberWithoutCode"
        val formattedNumber=repo.formatPhoneNumber(e164number,repo.getSimCountry().isoName)
        val formattedNumberWithoutCode=formattedNumber.removePrefix(code).trim()

        setContent()
        onPhoneNumberField().performTextInput(phoneNumberWithoutCode)

        onPhoneNumberField().assertTextEquals(formattedNumberWithoutCode)
    }



    @Test
    fun testOnTriggerEnterPhoneNumberEvent_formattedNumberAndUnknownCountry_shouldNotFormatNumberAndDisplaySameInput(){

        val formattedNumberWithoutCode="123 456 7899"

        setContent()
        onCountryCodeField().performTextReplacement("200")
        onPhoneNumberField().performTextInput(formattedNumberWithoutCode)

        onPhoneNumberField().assertTextEquals(formattedNumberWithoutCode)
    }


    @Test
    fun testOnTriggerEnterPhoneNumberEvent_formattedNumberAndCountryIsUnselected_shouldNotFormatNumberAndDisplaySameInput(){
        val formattedNumberWithoutCode="123 456 7899"

        setContent()
        onCountryCodeField().performTextClearance()
        onPhoneNumberField().performTextInput(formattedNumberWithoutCode)

        onPhoneNumberField().assertTextEquals(formattedNumberWithoutCode)
    }

    // TODO: Test phone number field when performing ime action should close keyboard and trigger on next btn click event

    private val queriedCountryInDialog=EGYPT

    @Test
    fun testDisplayCountriesDialogEvent_whenClickCountryField_shouldDisplayCountriesDialog(){
        setContent()
        onCountryField().performClick()
        onCountriesDialog().assertExists()
        onCountriesDialog().assert(hasParent(isDialog()))
    }
    
    @Test
    fun testDisplayCountriesDialogEvent_whenClickCountryField_shouldDisplayAllAvailableCountries(){
        setContent()
        onCountryField().performClick()
        repo.getAllCountries().forEach { country->
            assertCountryIsDisplayedOnDialog(country = country)
        }
    }

    @Test
    fun testCloseCountriesDialogEvent_whenClickOutsideDialog_shouldCloseCountriesDialog(){
        setContent()
        onCountryField().performClick()
        onCountriesDialog().performGesture {
            click(bottomCenter)
        }
        onCountriesDialog().assertDoesNotExist()
    }
    
    
    @Test
    fun testOnSelectCountryFromDialogEvent_whenSelectCountry_shouldCloseDialog(){
        setContent()

        onCountryField().performClick()
        composeTestRule.onNodeWithText("+${SAUDI_ARABIA.callingCode}").performClick()
        onCountriesDialog().assertDoesNotExist()
    }

    @Test
    fun testOnSelectCountryFromDialogEvent_whenSelectCountry_shouldUpdateCodeAndCountryFields(){
        setContent()

        onCountryField().performClick()

        onCountryOnDialog(PALESTINE).performClick()

        onCountryField().assertTextEquals(PALESTINE.name)
        onCountryCodeField().assertTextEquals(PALESTINE.callingCode.toString())

    }
    
    @Test
    fun testOnCountriesDialogQueryChangeEvent_enterCountryNameThatExists_shouldGetCountriesByName(){
        val query=queriedCountryInDialog.name[0].toLowerCase().toString()
        setContent()
        onCountryField().performClick()
        onCountriesDialogQueryField().performTextInput(query)
        onCountriesDialogQueryField().assertTextEquals(query)
        assertCountryIsDisplayedOnDialog(country = queriedCountryInDialog)

        repo.getAllCountries().minus(queriedCountryInDialog).forEach { country->
            composeTestRule.onNodeWithText(country.name).assertDoesNotExist()
            composeTestRule.onNodeWithText("+${country.callingCode}").assertDoesNotExist()
        }
    }

    @Test
    fun testOnCountriesDialogQueryChangeEvent_enterCountryNameThatDoesNotExist_shouldDisplayNoMatchingCountriesMessage(){

        setContent()
        onCountryField().performClick()
        onCountriesDialogQueryField().performTextInput("zz")
        onCountriesDialogQueryField().assertTextEquals("zz")
        composeTestRule.onNodeWithText(
            composeTestRule.stringResource(R.string.no_matching_countries)
        ).assertExists()
    }

    @Test
    fun testOnCountriesDialogQueryChangeEvent_clearFieldAfterInput_shouldDisplayAllCountries(){
        
        setContent()
        onCountryField().performClick()
        onCountriesDialogQueryField().performTextInput(queriedCountryInDialog.name[0].toLowerCase().toString())

        onCountriesDialogQueryField().performTextClearance()

        repo.getAllCountries().forEach { country->
            composeTestRule.onNodeWithText(country.name).assertExists()
            composeTestRule.onNodeWithText("+${country.callingCode}").assertExists()
        }
    }

    @Test
    fun testCountriesDialog_reopenDialogAfterSelectingQueriedCountry_shouldDisplayAllCountries(){
        setContent()

        onCountryField().performClick()

        onCountriesDialogQueryField().performTextInput(queriedCountryInDialog.name[0].toLowerCase().toString())

        composeTestRule.onNodeWithText("+${queriedCountryInDialog.callingCode}").performClick()

        onCountryField().performClick()

        onCountriesDialogQueryField().assertTextEquals("")
        
        repo.getAllCountries().forEach { country->
            composeTestRule.onNodeWithText(country.name).assertExists()
            composeTestRule.onNodeWithText("+${country.callingCode}").assertExists()
        }
    }

    // TODO: Test next button when click should trigger on next btn click event
    
}