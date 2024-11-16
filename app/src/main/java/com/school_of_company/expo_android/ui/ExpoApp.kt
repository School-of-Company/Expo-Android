package com.school_of_company.expo_android.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.school_of_company.design_system.component.navigation.ExpoNavigationBar
import com.school_of_company.design_system.component.navigation.ExpoNavigationBarItem
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.navigation.expoCreateRoute
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.expo_android.navigation.ExpoNavHost
import com.school_of_company.expo_android.navigation.TopLevelDestination

/**
 * 객체로 앱 상태를 중앙에서 관리하고, Scaffold와 BottomBar를 통해 유연한 UI 구성을 제공합니다.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpoApp(
    windowSizeClass: WindowSizeClass, // 화면 크기 정보를 받습니다.
    appState: ExpoAppState = rememberExpoAppState(windowSizeClass = windowSizeClass) // 화면 크기에 맞게 앱 상태를 기억합니다.
) {
    // BottomNavigationBar가 보여지는지 여부를 관리하는 상태입니다.
    val isBottomBarVisible = remember { mutableStateOf(true) }

    // 현재 네비게이션 백스택에서 가장 최근 목적지를 얻습니다.
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

    // 최상위 목적지들 중에 현재 보고 있는 화면이 있는 확인합니다.
    val topLevelDestinationRoute = arrayOf(
        homeRoute,
        expoCreateRoute
    )

    // 현재 목적지가 최상위 목적지 목록에 포함되면 BottomBar를 표시하도록 설정합니다.
    navBackStackEntry?.destination?.route?.let {
        isBottomBarVisible.value = topLevelDestinationRoute.contains(it)
    }

    ExpoAndroidTheme { colors, _ ->
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = colors.white,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                // BottomBar가 보여져야 하는 경우에만 표시합니다.
                if (isBottomBarVisible.value) {
                    ExpoBottomBar(
                        destinations = appState.topLevelDestinations, // 최상위 목적지 목록을 전달
                        onNavigateToDestination = appState::navigateToTopLevelDestination, // 네비게이션 함수
                        currentDestination = appState.currentDestination // 현재 목적지 정보
                    )
                }
            }
        ) { paddingValues ->
            // 네비게이션 호스트
            Box(modifier = Modifier.padding(paddingValues =
            paddingValues)) {
                ExpoNavHost(appState = appState)
            }
        }
    }
}

@Composable
fun ExpoBottomBar(
    destinations: List<TopLevelDestination>, // BottomBar에 표시될 최상위 목적지 목록
    onNavigateToDestination: (TopLevelDestination) -> Unit, // 사용자가 클릭했을 때 호출될 콜백
    currentDestination: NavDestination? // 현재 네비게이션 목적지
) {
    ExpoAndroidTheme { _, typography ->
        // 커스텀 네비게이션 바 구성 요소
        ExpoNavigationBar {
            // 각 최상위 목적지에 대한 아이템을 생성합니다.
            destinations.forEach { destination ->
                // 현재 목적지가 선택된 상태인지 확인
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

                // 각 네비게이션 바 아이템을 설정합니다.
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

// 현재 네비게이션 목적지가 최상위 목적지 계층에 속해 있는지 확인하는 함수
private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false // 목적지의 경로에 해당 최상위 목적지가 포함되어 있는지 확인
    } ?: false