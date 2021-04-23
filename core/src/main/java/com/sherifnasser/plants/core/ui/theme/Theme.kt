package com.sherifnasser.plants.core.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = RedA400,
    primaryVariant = DarkRedA400,
    secondary = TealA700
)

// Todo -> Handle dark theme

@Composable
fun PlantSTheme(content:@Composable ()->Unit) {
    MaterialTheme(
        colors = LightColorPalette,
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            content = content
        )
    }
}