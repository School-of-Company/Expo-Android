package com.school_of_company.expo_android.ui

import android.annotation.SuppressLint
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.school_of_company.design_system.component.navigation.ExpoNavigationBar
import com.school_of_company.design_system.component.navigation.ExpoNavigationBarItem
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.navigation.expoCreateRoute
import com.school_of_company.expo.navigation.expoCreatedRoute
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.expo_android.navigation.ExpoNavHost
import com.school_of_company.expo_android.navigation.TopLevelDestination
import com.school_of_company.user.navigation.profileRoute
import kotlinx.collections.immutable.ImmutableList

/**
 * 객체로 앱 상태를 중앙에서 관리하고, Scaffold와 BottomBar를 통해 유연한 UI 구성을 제공합니다.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExpoApp(
    startDestination: String,
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
        expoCreateRoute,
        expoCreatedRoute,
        profileRoute
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
                        topLevelDestinations = appState.topLevelDestinations, // 최상위 목적지 목록을 전달
                        onNavigateToDestination = appState::navigateToTopLevelDestination, // 네비게이션 함수
                        currentDestination = appState.currentDestination // 현재 목적지 정보
                    )
                }
            }
        ) { paddingValues ->
            // 네비게이션 호스트
            ExpoNavHost(
                modifier = Modifier.padding(paddingValues = paddingValues),
                startDestination = startDestination,
                appState = appState,
            )
        }
    }
}

@Composable
private fun ExpoBottomBar(
    modifier: Modifier = Modifier,
    topLevelDestinations: ImmutableList<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit, // 사용자가 클릭했을 때 호출될 콜백
    currentDestination: String? // 현재 네비게이션 목적지
) {
    ExpoAndroidTheme { _, typography ->
        // 커스텀 네비게이션 바 구성 요소
        ExpoNavigationBar(modifier = modifier) {
            // 각 최상위 목적지에 대한 아이템을 생성합니다.
            topLevelDestinations.forEach { destination ->
                // icon painter를 미리 변수로 추출
                val iconPainter = painterResource(id = destination.unSelectedIcon)
                // 현재 목적지가 선택된 상태인지 확인
                val isSelected = destination.routeName == currentDestination

                ExpoNavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = { Icon(painter = iconPainter, contentDescription = null) },
                    selectedIcon = { Icon(painter = iconPainter, contentDescription = null) },
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