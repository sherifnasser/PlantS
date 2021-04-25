package com.sherifnasser.plants.register.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sherifnasser.plants.core.ui.NavHostRoute
import com.sherifnasser.plants.register.presentation.ui.welcome.WelcomeScreen

/**
 * Nav host route for register module
 *
 * @param navController the main nav host controller from main nav host
 */
fun NavGraphBuilder.registerNavHost(navController: NavHostController){
    navigation(
        startDestination = RegisterNavScreen.Welcome.route,
        route = NavHostRoute.Register.route
    ){
        composable(RegisterNavScreen.Welcome.route){
            WelcomeScreen(navController = navController)
        }

        composable(RegisterNavScreen.TermsAndPrivacyPolicy.route){
            // Todo -> Add Terms & Privacy Policy Screen
        }

        composable(RegisterNavScreen.EnterPhoneNumber.route){
            // Todo -> Add Enter Phone Number Screen
        }
    }
}