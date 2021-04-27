package com.sherifnasser.plants.register.presentation.ui.welcome

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sherifnasser.plants.core.util.stringResource
import com.sherifnasser.plants.register.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestWelcomeScreen {

    @get:Rule
    val composeTestRule=createComposeRule()

    private var navigateToTermsAndPrivacyPolicyScreenInvokeCount=0

    private var navigateToEnterPhoneNumberScreenInvokeCount=0

    @Before
    fun setUp(){
        composeTestRule.setContent {
            WelcomeScreen(
                navigateToTermsAndPrivacyPolicyScreen = {
                    navigateToTermsAndPrivacyPolicyScreenInvokeCount++
                },
                navigateToEnterPhoneNumberScreen = {
                    navigateToEnterPhoneNumberScreenInvokeCount++
                }
            )
        }
    }

    @Test
    fun clickTermsAndPrivacyPolicy_navigateToTermsAndPrivacyPolicyScreen(){


        val termsAndPrivacyPolicyText=composeTestRule.stringResource(id=R.string.terms_and_privacy_policy)

        composeTestRule.onNodeWithText(termsAndPrivacyPolicyText).performClick()

        assertThat(navigateToTermsAndPrivacyPolicyScreenInvokeCount).isEqualTo(1)

    }

    @Test
    fun clickContinueBtn_navigateToEnterPhoneNumberScreen(){

        val continueBtnText=composeTestRule.stringResource(id=R.string.continue_str)

        composeTestRule.onNodeWithText(continueBtnText).performClick()

        assertThat(navigateToEnterPhoneNumberScreenInvokeCount).isEqualTo(1)

    }
}