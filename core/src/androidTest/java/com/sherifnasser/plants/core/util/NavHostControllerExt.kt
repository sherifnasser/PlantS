package com.sherifnasser.plants.core.util

import androidx.navigation.NavHostController
import androidx.navigation.compose.getBackStackEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

fun NavHostController.assertCurrentRouteIs(route:String){
    val expectedBackStackEntry=getBackStackEntry(route)
    assertEquals(expectedBackStackEntry.destination.id,currentDestination!!.id)
}

fun NavHostController.assertCurrentRouteIsNot(route:String){
    val unexpectedBackStackEntry=getBackStackEntry(route)
    assertNotEquals(unexpectedBackStackEntry.destination.id,currentDestination!!.id)
}