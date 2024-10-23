package com.school_of_company.expo_android.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.school_of_company.expo_android.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberExpoAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) : ExpoAppState {
    return remember(
        windowSizeClass,
        coroutineScope,
        navController
    ) {
        ExpoAppState(
            windowSizeClass = windowSizeClass,
            navController = navController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class ExpoAppState(
    val windowSizeClass: WindowSizeClass,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            // add TopLevelDestination Route
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                // add TopLevelDestination
                else -> null
            }
        }
    }
}

fun NavController.navigationPopUpToLogin(loginRoute: String) {
    this.navigate(loginRoute) {
        popUpTo(loginRoute) { inclusive = true }
    }
}