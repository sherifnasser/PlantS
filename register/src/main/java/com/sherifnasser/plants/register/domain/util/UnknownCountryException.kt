package com.sherifnasser.plants.register.domain.util

class UnknownCountryException:IllegalArgumentException{
    constructor(callingCode:Int):super("There is no country with calling code +$callingCode")
    constructor(isoName:String):super("There is no country with iso name $isoName")
}