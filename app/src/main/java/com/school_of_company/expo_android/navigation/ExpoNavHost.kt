package com.school_of_company.expo_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.school_of_company.expo_android.ui.ExpoAppState

@Composable
fun ExpoNavHost(
    modifier: Modifier = Modifier,
    appState: ExpoAppState,
    startDestination: String = ""
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

    }
}