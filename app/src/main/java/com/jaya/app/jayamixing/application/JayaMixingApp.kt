package com.jaya.app.jayamixing.application

import android.app.Application
import com.jaya.app.jayamixing.utility.Metar
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JayaMixingApp:Application() {

    override fun onCreate() {
        super.onCreate()
        Metar.initialize(this)
        app = this
    }

    companion object {
        // lateinit var appInstance: JayaPackagingApp
        var app: JayaMixingApp? = null
    }
}