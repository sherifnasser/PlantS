package com.sherifnasser.plants.core.util

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule

/**
 * To access strings with [ComposeTestRule]
 */
fun ComposeTestRule.stringResource(id:Int):String=
    (this as AndroidComposeTestRule<*,*>).activity.getString(id)