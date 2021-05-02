package com.sherifnasser.plants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sherifnasser.plants.R
import com.sherifnasser.plants.core.ui.NavHostRoute
import com.sherifnasser.plants.register.presentation.registerNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantSMainActivity:ComponentActivity(){

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState:Bundle?){
        setTheme(R.style.Theme_PlantS)
        super.onCreate(savedInstanceState)

        setContent{
            val navController=rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavHostRoute.Register.route
            ){

                registerNavHost(navController = navController)

            }
        }
    }

}
