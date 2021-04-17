package com.sherifnasser.plants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sherifnasser.plants.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:ComponentActivity(){

    override fun onCreate(savedInstanceState:Bundle?){
        setTheme(R.style.Theme_PlantS)
        super.onCreate(savedInstanceState)

        setContent{

        }
    }
}