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
import com.school_of_company.home.navigation.navigateToExpoCreate
import com.school_of_company.home.navigation.navigateToHome
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberExpoAppState(
    windowSizeClass: WindowSizeClass, // 화면 크기 분류 정보를 받습니다.
    navController: NavHostController = rememberNavController(), // 네비게이션 컨트롤러를 생성합니다.
    coroutineScope: CoroutineScope = rememberCoroutineScope() // 코루틴 스코프를 생성합니다.
) : ExpoAppState {
    // windowSizeClass와 navController, coroutineScope가 변경될 때만 재생성하는 ExpoAppState를 기억합니다.
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
    val windowSizeClass: WindowSizeClass, // 화면 크기 분류 정보를 받습니다.
    val navController: NavHostController, // 네비게이션 컨트롤러를 생성합니다.
    val coroutineScope: CoroutineScope // 코루틴 스코프를 생성합니다.
) {
    // 현재 네비게이션 목적지를 얻습니다. 네비게이션 백스택에서 현재 활성화된 목적지를 반환
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    // 현재 화면이 최상위 목적지인지 여부를 반환합니다.
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            else -> null
        }

    // Compact 모드일 때만 BottomBar를 표시합니다.
    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    // 최상위 네비게이션 목적지 목록입니다.
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    // 최상위 목적지로 네비게이션하는 함수입니다.
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // 시작 목적지로 이동할 때 백스택을 비우고, 상태를 복원합니다.
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true // 여러 번 클릭해도 같은 화면이 여러 번 쌓이지 않게 합니다.
                restoreState = true // 이번에 방문한 목적지의 상태를 복원합니다.
            }
            when (topLevelDestination) {
                TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                TopLevelDestination.EXPO -> navController.navigateToExpoCreate(topLevelNavOptions)
                else -> null
            }
        }
    }
}

// 로그인 화면으로 이동할 때 네비게이션 백스택을 비우고 이동하는 함수입니다.
fun NavController.navigationPopUpToLogin(loginRoute: String) {
    this.navigate(loginRoute) {
        popUpTo(loginRoute) { inclusive = true }
    }
}