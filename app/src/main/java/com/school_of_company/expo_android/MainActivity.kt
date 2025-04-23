package com.school_of_company.expo_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.school_of_company.expo.navigation.homeRoute
import com.school_of_company.expo_android.ui.ExpoApp
import com.school_of_company.navigation.sigInRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var backPressedTime: Long = 0
    private val BACK_PRESSED_DURATION: Long = 2000L

    private val viewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.appLoginState.value is AppLoginState.Loading
        }

        enableEdgeToEdge()

        setContent {
            if (viewModel.appLoginState.value is AppLoginState.Loading) return@setContent

            val startDestination = when (viewModel.appLoginState.value) {
                is AppLoginState.Success -> homeRoute
                else -> sigInRoute
            }

            ExpoApp(
                startDestination = startDestination,
                windowSizeClass = calculateWindowSizeClass(activity = this),
            )
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime < BACK_PRESSED_DURATION) {
                    finishAffinity()
                } else {
                    Toast.makeText(this@MainActivity, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    backPressedTime = currentTime
                }
            }
        })
    }
}