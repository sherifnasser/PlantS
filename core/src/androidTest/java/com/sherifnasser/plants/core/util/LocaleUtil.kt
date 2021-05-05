package com.sherifnasser.plants.core.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.util.Locale

@Suppress("DEPRECATION")
fun setSystemLocaleForTest(locale: Locale){
    val ctx= ApplicationProvider.getApplicationContext<Context>()
    val res=ctx.resources
    val config=res.configuration
    config.setLocale(locale)
    ctx.createConfigurationContext(config)
    res.updateConfiguration(config,res.displayMetrics)
}