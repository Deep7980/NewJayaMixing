package com.jaya.app.jayamixing.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jaya.app.core.common.Destination
import com.jaya.app.core.utils.NavigationIntent
import com.jaya.app.jayamixing.presentation.ui.screen.DashboardScreen
import com.jaya.app.jayamixing.presentation.ui.screen.LoginScreen
import com.jaya.app.jayamixing.presentation.ui.screen.SplashScreen
import com.jaya.app.jayamixing.presentation.viewmodels.BaseViewModel
import kotlinx.coroutines.channels.Channel

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    navigationChannel: Channel<NavigationIntent>,
    paddingValues: PaddingValues,
    baseViewModel: BaseViewModel
){
    navHostController.NavEffects(navigationChannel)

    AppNavHost(
        navController = navHostController,
        startDestination = Destination.Splash,
        modifier = Modifier.padding(paddingValues),
        enterTransition = AppScreenTransitions.ScreenEnterTransition,
        popEnterTransition = AppScreenTransitions.ScreenPopEnterTransition,
        exitTransition = AppScreenTransitions.ScreenExitTransition,
        popExitTransition = AppScreenTransitions.ScreenPopExitTransition,
    ) {

        composable(destination = Destination.Splash) {
            SplashScreen()
        }

        composable(destination = Destination.Login) {
            LoginScreen(baseViewModel)
        }
//
//        composable(destination = Destination.Otp) {
//            OtpScreen(baseViewModel)
//        }
        composable(destination = Destination.Dashboard) {
            DashboardScreen(baseViewModel)
        }
//        composable(destination = Destination.AddProduct) {
//            AddProductScreen(baseViewModel)
//        }
//        composable(destination = Destination.CaptureVideo) {
//            VideoCaptureScreen(baseViewModel)
//        }


    }
}