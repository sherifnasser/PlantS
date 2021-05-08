package com.sherifnasser.plants.register.presentation.ui.phone

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.sherifnasser.plants.core.ui.components.PlantSOutlinedTextField
import com.sherifnasser.plants.core.ui.theme.PlantSTheme
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.presentation.ui.phone.event.EnterPhoneNumberEvent
import com.sherifnasser.plants.register.presentation.ui.phone.state.CountryState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EnterPhoneNumberScreen(
    viewModel: EnterPhoneNumberViewModel
){
    PlantSTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 32.dp,
                    vertical = 72.dp
                )
        ){

            val (text1Ref,text2Ref,countryFieldRef,phoneNumberRowRef,nextBtnRef)=createRefs()
            val isCountriesDialogShown=viewModel.isCountriesDialogShown.value

            EnterPhoneNumberToGetStarted(ref = text1Ref)

            YouWillReceiveVerificationCode(ref = text2Ref, linkTopToBottomOfRef = text1Ref)

            CountryField(
                ref = countryFieldRef,
                linkTopToBottomOfRef = text2Ref,
                countryState = viewModel.countryState.value,
                onDisplayCountriesDialogRequest = {
                    viewModel.onTriggerEvent(EnterPhoneNumberEvent.DisplayCountriesDialog)
                },
                isFocusable = !isCountriesDialogShown
            )

            PhoneNumberRow(
                ref = phoneNumberRowRef,
                linkTopToBottomOfRef = countryFieldRef,
                code = viewModel.countryCodeText.value,
                onCodeChange = {
                    viewModel.onTriggerEvent(
                        EnterPhoneNumberEvent.EnterCountryCode(code = it)
                    )
                },
                phoneNumber = viewModel.phoneNumberText.value,
                onPhoneNumberChange = {
                    viewModel.onTriggerEvent(
                        EnterPhoneNumberEvent.EnterNumber(number = it)
                    )
                },
                phoneNumberFocusRequester = viewModel.phoneNumberFocusRequester.value,
                keyboardController = LocalSoftwareKeyboardController.current
            )

            NextButton(ref = nextBtnRef, linkTopToBottomOfRef = phoneNumberRowRef)

            CountriesDialog(isShown = isCountriesDialogShown) {
                viewModel.onTriggerEvent(EnterPhoneNumberEvent.CloseCountriesDialog)
            }
        }
    }
}

@Composable
private fun ConstraintLayoutScope.EnterPhoneNumberToGetStarted(
    ref:ConstrainedLayoutReference
){
    Text(
        text = stringResource(R.string.enter_your_phone_number_to_get_started),
        style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
        modifier = Modifier.constrainAs(ref = ref){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    )
}

@Composable
private fun ConstraintLayoutScope.YouWillReceiveVerificationCode(
    ref:ConstrainedLayoutReference,
    linkTopToBottomOfRef:ConstrainedLayoutReference
){
    Text(
        text = stringResource(id = R.string.you_will_receive_a_verification_code_carrier_rates_may_apply),
        style = TextStyle(textAlign = TextAlign.Center),
        modifier = Modifier.constrainAs(ref = ref) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(linkTopToBottomOfRef.bottom, 16.dp)
        })
}

@Composable
private fun ConstraintLayoutScope.CountryField(
    ref:ConstrainedLayoutReference,
    linkTopToBottomOfRef:ConstrainedLayoutReference,
    countryState: CountryState,
    onDisplayCountriesDialogRequest:()->Unit,
    isFocusable:Boolean
){

    val countryText=when(countryState){
        CountryState.Unselected->stringResource(id = R.string.select_your_country)
        is CountryState.Selected->countryState.country.name
        CountryState.Unknown->stringResource(id = R.string.unknown_country)
    }

    OutlinedTextField(
        readOnly = true,
        value = countryText,
        onValueChange = {},
        singleLine=true,
        trailingIcon = {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "",tint = Color.Black)
        },
        modifier = Modifier
            .fillMaxWidth()
            .constrainAs(ref = ref) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(linkTopToBottomOfRef.bottom, 16.dp)
            }
            .onFocusEvent {
                if (it == FocusState.Active)
                    onDisplayCountriesDialogRequest.invoke()
            }
            .focusable(enabled = isFocusable)
    )
}

@ExperimentalComposeUiApi
@Composable
private fun ConstraintLayoutScope.PhoneNumberRow(
    ref:ConstrainedLayoutReference,
    linkTopToBottomOfRef:ConstrainedLayoutReference,
    code:String,
    onCodeChange:(String)->Unit,
    phoneNumber:String,
    onPhoneNumberChange:(String)->Unit,
    phoneNumberFocusRequester:FocusRequester,
    keyboardController: SoftwareKeyboardController?
){
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .constrainAs(ref = ref) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(linkTopToBottomOfRef.bottom, 16.dp)
            }
    ){


        PlantSOutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Add,contentDescription = "",tint=Color.Black)
            },
            modifier = Modifier
                .weight(1f)
                .focusOrder { next = phoneNumberFocusRequester }
            ,
            contentHorizontalPadding = 0.dp
        )

        Spacer(modifier = Modifier.padding(start = 12.dp))


        OutlinedTextField(
            value = TextFieldValue(
                text = phoneNumber,
                selection = TextRange(index = phoneNumber.length) // Make cursor always at the end
            ),
            onValueChange = {
                onPhoneNumberChange.invoke(it.text)
            },
            singleLine=true,
            label={
                Text(text = stringResource(R.string.phone_number))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    btnOnClick("Keyboard")
                }
            ),
            modifier = Modifier
                .weight(3f)
                .focusRequester(phoneNumberFocusRequester)
        )
    }
}

@Composable
private fun ConstraintLayoutScope.NextButton(
    ref:ConstrainedLayoutReference,
    linkTopToBottomOfRef:ConstrainedLayoutReference
){
    Button(
        onClick = { btnOnClick("Button")},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .constrainAs(ref = ref) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(linkTopToBottomOfRef.bottom, 16.dp)
            }
    ) {
        Text(text = stringResource(R.string.next))
    }
}

private const val TAG = "EnterPhoneNumberScreen"

private fun btnOnClick(from:String){
    Log.d(TAG, "btnOnClick: $from")
}