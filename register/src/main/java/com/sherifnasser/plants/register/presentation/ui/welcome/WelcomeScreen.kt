package com.sherifnasser.plants.register.presentation.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.sherifnasser.plants.core.ui.theme.PlantSTheme
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.presentation.RegisterNavScreen

@Composable
internal fun WelcomeScreen(navController:NavHostController?=null){
    PlantSTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(
                imageVector = ImageVector.vectorResource(id = com.sherifnasser.plants.core.R.drawable.ic_plant_s),
                contentDescription = stringResource(R.string.plant_s_icon)
            )

            Text(
                text = stringResource(R.string.plant_plants_with_plant_s),
                style = TextStyle(fontSize = 36.sp, textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.padding(top = 50.dp))

            Text(
                text = stringResource(R.string.terms_and_privacy_policy),
                color = Color(0xFF0645AD)
            )

            Spacer(modifier = Modifier.padding(bottom = 50.dp))

            Button(
                onClick = {
                    navController?.navigate(RegisterNavScreen.EnterPhoneNumber.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.continue_str),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 50.dp))
        }
    }
}

@Composable
fun PreviewWelcomeScreen()=WelcomeScreen()