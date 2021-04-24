package com.sherifnasser.plants.register.presentation

import com.sherifnasser.plants.core.ui.NavHostRoute

/**
 * Handle all screens in register nav host
 *
 * @param route route for the screen
 *
 * @see NavHostRoute.Register
 */
internal sealed class RegisterNavScreen(val route:String){

    object Welcome:RegisterNavScreen("Welcome")

    object TermsAndPrivacyPolicy:RegisterNavScreen("Terms & Privacy Policy")

    object EnterPhoneNumber:RegisterNavScreen("Enter phone number")
}
