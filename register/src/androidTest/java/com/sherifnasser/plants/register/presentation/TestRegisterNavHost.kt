package com.sherifnasser.plants.register.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sherifnasser.plants.core.util.assertCurrentRouteIs
import com.sherifnasser.plants.register.util.runTestRegisterNavHost
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestRegisterNavHost{
    @get:Rule
    val composeTestRule=createComposeRule()


    @Test
    fun runRegisterNavHost_displayWelcomeScreen(){
        composeTestRule.runTestRegisterNavHost().assertCurrentRouteIs(RegisterNavScreen.Welcome.route)
    }
}