package com.jaya.app.jayamixing.utility

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.jaya.app.jayamixing.application.JayaMixingApp

object Metar {

    private var app: JayaMixingApp? = null
    operator fun get(key: String): String {
        return try {
            val ai = if (Build.VERSION.SDK_INT >= 33) {
                (app as Context)
                    .packageManager
                    .getApplicationInfo(
                        app?.applicationInfo?.packageName!!,
                        PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
                    )
            } else {
                (app as Context)
                    .packageManager
                    .getApplicationInfo(
                        (app as Context)
                            .packageName,
                        PackageManager.GET_META_DATA
                    )
            }
            val bundle = ai.metaData
            bundle.getString(key) ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    fun initialize(app: JayaMixingApp) {
        this.app = app
    }
}