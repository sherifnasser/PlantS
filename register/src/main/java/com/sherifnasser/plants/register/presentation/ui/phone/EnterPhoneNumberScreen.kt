package com.sherifnasser.plants.register.presentation.ui.phone

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.sherifnasser.plants.core.ui.components.PlantSOutlinedTextField
import com.sherifnasser.plants.core.ui.theme.PlantSTheme
import com.sherifnasser.plants.register.R

@ExperimentalComposeUiApi
@Composable
fun EnterPhoneNumberScreen(){
    PlantSTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 32.dp,
                    vertical = 72.dp
                )

        ){

            val phoneNumberFocus = remember {FocusRequester()}
            val keyboardController=LocalSoftwareKeyboardController.current
            val (text1,text2,country,phoneNumber,nextBtn)=createRefs()
            var countryText by remember { mutableStateOf("") }
            var showDialog by remember { mutableStateOf(false) }
            val onDismiss ={showDialog=false}
            var countryCodeText by remember { mutableStateOf("") }
            var phoneNumberText by remember { mutableStateOf("") }

            Text(
                text = stringResource(R.string.enter_your_phone_number_to_get_started),
                style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
                modifier = Modifier.constrainAs(text1){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )



            Text(
                text = stringResource(id = R.string.you_will_receive_a_verification_code_carrier_rates_may_apply),
                style = TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(text1.bottom, 16.dp)
                })


            OutlinedTextField(
                readOnly = true,
                value = countryText,
                onValueChange = {countryText=it},
                singleLine=true,
                trailingIcon = {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "",tint = Color.Black)
                },
                placeholder={
                    Text(text = stringResource(R.string.select_your_country))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(country) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(text2.bottom, 16.dp)
                    }
                    .onFocusEvent {
                        if (it == FocusState.Active)
                            showDialog = true
                    }
                    .focusable(enabled = !showDialog)
            )


            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .constrainAs(phoneNumber) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(country.bottom, 16.dp)
                    }
            ){


                PlantSOutlinedTextField(
                    value = countryCodeText,
                    onValueChange = {
                        if(it.length<=3)
                            countryCodeText=it
                    },
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
                        .focusOrder { next = phoneNumberFocus }
                    ,
                    contentHorizontalPadding = 0.dp
                )

                Spacer(modifier = Modifier.padding(start = 12.dp))

                OutlinedTextField(
                    value = phoneNumberText,
                    onValueChange = {phoneNumberText=it},
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
                        .focusRequester(phoneNumberFocus)
                )
            }


            Button(
                onClick = { btnOnClick("Button")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(nextBtn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(phoneNumber.bottom, 16.dp)
                    }
            ) {
                Text(text = stringResource(R.string.next))
            }

            if(showDialog){
                Dialog(onDismissRequest = onDismiss) {
                    Card{
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Hi")
                            Button(onClick = onDismiss) {
                                Text(text = "Cancel")
                            }
                        }
                    }
                }
            }
        }

    }
}

private const val TAG = "EnterPhoneNumberScreen"

private fun btnOnClick(from:String){
    Log.d(TAG, "btnOnClick: $from")
}