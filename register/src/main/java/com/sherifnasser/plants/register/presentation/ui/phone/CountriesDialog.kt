package com.sherifnasser.plants.register.presentation.ui.phone

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.domain.model.Country

@ExperimentalComposeUiApi
@Composable
internal fun CountriesDialog(
    isShown:Boolean,
    onDismissRequest:()->Unit,
    query:String,
    onQueryChange:(String)->Unit,
    displayedCountries:List<Country>,
    onCountrySelected:(Country)->Unit
){
    if(isShown){
        Dialog(onDismissRequest = onDismissRequest) {
            Card(shape = RoundedCornerShape(12.dp)){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    SearchCountriesTextField(
                        query = query,
                        onQueryChange = onQueryChange
                    )

                    Spacer(modifier = Modifier.padding(top = 16.dp))

                    CountriesList(
                        displayedCountries = displayedCountries,
                        onCountrySelected = {
                            onCountrySelected.invoke(it)
                            onDismissRequest.invoke()
                        }
                    )

                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@Composable
private fun SearchCountriesTextField(
    query:String,
    onQueryChange:(String)->Unit
){
    val keyboardController=LocalSoftwareKeyboardController.current

    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)){
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(text = stringResource(R.string.search))
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            colors = textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            ),
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .border(width = 0.5.dp,color = Color.DarkGray,shape=CircleShape)
        )
    }
}

@Composable
private fun CountriesList(displayedCountries:List<Country>,onCountrySelected:(Country)->Unit){

    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.75f),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 0.dp)
    ){

        items(displayedCountries){country->

            Card(
                modifier = Modifier.clickable {
                    onCountrySelected.invoke(country)
                }
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    val textStyle=TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = country.name,
                        style = textStyle
                    )


                    Text(
                        text = "+${country.callingCode}",
                        style = textStyle
                    )
                }

                Divider(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}