package com.sherifnasser.plants.register.presentation.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.sherifnasser.plants.core.ui.theme.PlantSTheme
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.presentation.RegisterNavScreen

@Composable
internal fun WelcomeScreen(navController:NavHostController){
    PlantSTheme {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 36.dp,
                    vertical = 24.dp
                )
        ) {

            val (plantSIcon,text,terms,continueBtn)=createRefs()

            Image(
                imageVector = ImageVector.vectorResource(id = com.sherifnasser.plants.core.R.drawable.ic_plant_s),
                contentDescription = stringResource(R.string.plant_s_icon),
                modifier = Modifier.constrainAs(plantSIcon){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(text.top)
                }
            )

            Text(
                text = stringResource(R.string.plant_plants_with_plant_s),
                style = TextStyle(fontSize = 36.sp, textAlign = TextAlign.Center),
                modifier = Modifier.constrainAs(text){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(terms.top,48.dp)
                }
            )


            Text(
                text = stringResource(R.string.terms_and_privacy_policy),
                color = Color(0xFF0645AD),
                modifier = Modifier
                    .constrainAs(terms) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(continueBtn.top, 24.dp)
                    }
                    .clickable {
                        navController.navigate(RegisterNavScreen.TermsAndPrivacyPolicy.route)
                    }
            )

            Button(
                onClick = {
                    navController.navigate(RegisterNavScreen.EnterPhoneNumber.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(continueBtn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.continue_str),
                    color = Color.White
                )
            }

        }

    }
}