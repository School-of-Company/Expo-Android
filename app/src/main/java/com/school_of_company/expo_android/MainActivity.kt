package com.school_of_company.expo_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo_android.ui.ExpoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린을 표기합니다. 앱 시작 시 기본 스플래스 화면을 설정.
        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider {
                ExpoAndroidTheme { _, _ ->
                    ExpoApp(windowSizeClass = calculateWindowSizeClass(activity = this))
                }
            }
        }
    }
}