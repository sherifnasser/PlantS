package com.sherifnasser.plants.register.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.navigation.NavHostController
import com.sherifnasser.plants.core.ui.NavHostRoute
import com.sherifnasser.plants.core.util.runTestNavHost
import com.sherifnasser.plants.register.presentation.registerNavHost

/**
 * Run register nav host for tests
 *
 * @param block a block to be invoked to use composable functions like stringResource(), etc
 *
 * @see runTestNavHost
 */

internal fun ComposeContentTestRule.runTestRegisterNavHost(
    block:@Composable ()->Unit={}
): NavHostController =
    runTestNavHost(
        navHostRoute = NavHostRoute.Register,
        navHostContent = {registerNavHost(navController = it)},
        block = block
    )