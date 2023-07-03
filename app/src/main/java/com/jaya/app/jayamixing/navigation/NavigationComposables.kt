package com.jaya.app.jayamixing.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jaya.app.core.common.Destination

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    route: String? = null,
    enterTransition: AppScreenTransitions.ScreenEnterTransition,
    popEnterTransition: AppScreenTransitions.ScreenPopEnterTransition,
    exitTransition: AppScreenTransitions.ScreenExitTransition,
    popExitTransition: AppScreenTransitions.ScreenPopExitTransition,
    builder: NavGraphBuilder.() -> Unit,
) {
    Log.d("APPNavigation", startDestination.fullRoute)
    NavHost(
        navController = navController,
        startDestination = startDestination.fullRoute,
        modifier = modifier,
        route = route,
        builder = builder
    )
}


fun NavGraphBuilder.composable(
    destination: Destination,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        deepLinks = deepLinks,
        arguments = arguments,
        route = destination.fullRoute,
        content = content,
    )
}