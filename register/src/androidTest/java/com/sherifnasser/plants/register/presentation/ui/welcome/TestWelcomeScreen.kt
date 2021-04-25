package com.sherifnasser.plants.register.presentation.ui.welcome

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sherifnasser.plants.core.util.assertCurrentRouteIs
import com.sherifnasser.plants.core.util.assertCurrentRouteIsNot
import com.sherifnasser.plants.register.R
import com.sherifnasser.plants.register.presentation.RegisterNavScreen
import com.sherifnasser.plants.register.util.runTestRegisterNavHost
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestWelcomeScreen {

    @get:Rule
    val composeTestRule=createComposeRule()

    @Test
    fun clickTermsAndPrivacyPolicy_navigateToTermsAndPrivacyPolicyScreen(){
        lateinit var termsAndPrivacyPolicyText:String
        val navController=composeTestRule.runTestRegisterNavHost{
            termsAndPrivacyPolicyText=stringResource(R.string.terms_and_privacy_policy)
        }

        composeTestRule.onNodeWithText(termsAndPrivacyPolicyText).performClick()

        navController.assertCurrentRouteIsNot(RegisterNavScreen.Welcome.route)
        navController.assertCurrentRouteIs(RegisterNavScreen.TermsAndPrivacyPolicy.route)
    }

    @Test
    fun clickContinueBtn_navigateToEnterPhoneNumberScreen(){

        lateinit var continueBtnText:String

        val navController=composeTestRule.runTestRegisterNavHost{
            continueBtnText=stringResource(R.string.continue_str)
        }

        composeTestRule.onNodeWithText(continueBtnText).performClick()

        navController.assertCurrentRouteIsNot(RegisterNavScreen.Welcome.route)
        navController.assertCurrentRouteIs(RegisterNavScreen.EnterPhoneNumber.route)
    }
}