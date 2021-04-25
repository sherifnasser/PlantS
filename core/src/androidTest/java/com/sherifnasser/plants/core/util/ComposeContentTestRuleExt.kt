package com.sherifnasser.plants.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sherifnasser.plants.core.ui.NavHostRoute

/**
 * Run a nav host navigation for tests
 *
 * @param navHostRoute the route of nav host
 * @param navHostContent the builder of nav host
 * @param block a block to be invoked to use composable functions like stringResource(), etc
 *
 * @return the nav controller associated with nav host
 */

fun ComposeContentTestRule.runTestNavHost(
    navHostRoute: NavHostRoute,
    navHostContent: NavGraphBuilder.(NavHostController)->Unit,
    block: @Composable ()->Unit
):NavHostController{

    lateinit var navController:NavHostController

    setContent{

        navController=rememberNavController()

        NavHost(navController=navController,startDestination=navHostRoute.route){
            navHostContent(navController)
        }

        block.invoke()

    }

    return navController
}