package com.school_of_company.expo_android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.school_of_company.design_system.component.navigation.ExpoNavigationBar
import com.school_of_company.design_system.component.navigation.ExpoNavigationBarItem
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo_android.navigation.ExpoNavHost
import com.school_of_company.expo_android.navigation.TopLevelDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpoApp(
    windowSizeClass: WindowSizeClass,
    appState: ExpoAppState = rememberExpoAppState(
        windowSizeClass = windowSizeClass
    )
) {
    val isBottomBarVisible = remember { mutableStateOf(true) }
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val topLevelDestinationRoute = arrayOf(
        TopLevelDestination.EXPO // Example Cold
    )

    navBackStackEntry?.destination?.route?.let {
        isBottomBarVisible.value = topLevelDestinationRoute.contains(TopLevelDestination.EXPO) // Example Code <- contains()
    }

    ExpoAndroidTheme { colors, _ ->
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = colors.white,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (isBottomBarVisible.value) {
                    ExpoBottomBar(
                        destination = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination
                    )
                }
            }
        ) { _ ->
            ExpoNavHost(appState = appState)
        }
    }
}

@Composable
fun ExpoBottomBar(
    destination: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    ExpoAndroidTheme { _, typography ->
        ExpoNavigationBar {
            destination.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

                ExpoNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.unSelectedIcon),
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = destination.unSelectedIcon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = destination.iconText,
                            style = typography.captionRegular2
                        )
                    }
                )
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false