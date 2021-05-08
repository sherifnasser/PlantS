package com.sherifnasser.plants.register.presentation.ui.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
internal fun CountriesDialog(
    isShown:Boolean,
    onDismiss:()->Unit
){
    if(isShown){
        Dialog(onDismissRequest = onDismiss) {
            Card{
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Hi")
                    Button(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}