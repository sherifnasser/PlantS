package com.sherifnasser.plants.core.ui

/**
 * Handle all nested nav hosts
 * @param route route for the nav host
 */
sealed class NavHostRoute(val route:String){

    /**
     * Nav host route for register module
     */
    object Register: NavHostRoute("Register")

}
