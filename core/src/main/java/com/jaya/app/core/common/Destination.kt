package com.jaya.app.core.common

import android.util.Log

sealed class Destination(protected val route: String, vararg arguments: Any) {

    val fullRoute: String = if (arguments.isEmpty()) route else {
        val builder = StringBuilder(route)
        arguments.forEach { builder.append("/{${it}}") }
        Log.e("FullRoute", builder.toString())
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object None : Destination("")
    object Splash : NoArgumentsDestination(AppRoutes.SPLASH)
    object Login : NoArgumentsDestination(AppRoutes.LOGIN)
    object Otp : NoArgumentsDestination(AppRoutes.OTP)
    object Dashboard : NoArgumentsDestination(AppRoutes.DASHBOARD)
    object AddProduct : NoArgumentsDestination(AppRoutes.ADD_PRODUCT)

    object Completed: NoArgumentsDestination(AppRoutes.COMPLETED)


//    object CaptureVideo : NoArgumentsDestination(AppRoutes.CAPTURE_VIDEO)

}

private fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            if (arg.isNotEmpty()) {
                builder.append("/$arg")
            }

        }
    }
    return builder.toString()
}