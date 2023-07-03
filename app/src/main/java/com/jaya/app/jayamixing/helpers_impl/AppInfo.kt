package com.jaya.app.jayamixing.helpers_impl


import com.jaya.app.core.helpers.Info
import com.jaya.app.jayamixing.BuildConfig
import javax.inject.Inject

class AppInfo @Inject constructor():Info {

        override fun getCurrentVersion(): Int {

            return BuildConfig.VERSION_CODE
        }
}