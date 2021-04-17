package com.sherifnasser.plants.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = RedA400,
    primaryVariant = DarkRedA400,
    secondary = TealA700
)

// Todo -> Handle dark theme

@Composable
fun PlantSTheme(content:@Composable()()->Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        content = content
    )
}