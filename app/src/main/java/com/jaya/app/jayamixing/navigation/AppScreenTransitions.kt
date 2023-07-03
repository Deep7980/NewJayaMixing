package com.jaya.app.jayamixing.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
sealed class AppScreenTransitions<T> {
    object ScreenEnterTransition : AppScreenTransitions<AnimatedContentScope<NavBackStackEntry>>() {
        operator fun invoke(backStackEntry: NavBackStackEntry): EnterTransition {
            return when(backStackEntry.destination.route) {
                else -> {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
            }
        }
    }

    object ScreenPopEnterTransition :
        AppScreenTransitions<AnimatedContentScope<NavBackStackEntry>>() {
        operator fun invoke(backStackEntry: NavBackStackEntry): EnterTransition {
            return when(backStackEntry.destination.route) {
                else -> {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                }
            }
        }
    }

    object ScreenExitTransition : AppScreenTransitions<AnimatedContentScope<NavBackStackEntry>>() {
        operator fun invoke(backStackEntry: NavBackStackEntry): ExitTransition {
            return when(backStackEntry.destination.route) {
                else -> {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            }
        }
    }

    object ScreenPopExitTransition :
        AppScreenTransitions<AnimatedContentScope<NavBackStackEntry>>() {
        operator fun invoke(backStackEntry: NavBackStackEntry): ExitTransition {
            return when(backStackEntry.destination.route) {
                else -> {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            }
        }
    }

}
