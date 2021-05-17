package com.sherifnasser.plants.core.util

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription

/**
 * To access strings with [ComposeTestRule] that implements [SemanticsNodeInteractionsProvider]
 */
fun SemanticsNodeInteractionsProvider.stringResource(id:Int):String=
    (this as AndroidComposeTestRule<*,*>).activity.getString(id)

/**
 * To access nodes with content description [labelResId]
 */
fun SemanticsNodeInteractionsProvider.onNodeWithContentDescription(
    @StringRes labelResId: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false
): SemanticsNodeInteraction=
    onNodeWithContentDescription(
        label = stringResource(labelResId),
        substring = substring,
        ignoreCase = ignoreCase,
        useUnmergedTree = useUnmergedTree
    )
