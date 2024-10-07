package com.school_of_company.convention

import com.school_of_company.convention.expo.configureKotlinAndroid
import com.school_of_company.convention.expo.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

// Plugin class for configuring Android application project settings
class AndroidApplicationConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary plugins for Android and Kotlin
            with(pluginManager) {
                apply("com.android.application") // Apply Android library plugin
                apply("org.jetbrains.kotlin.android") // Apply Kotlin Android plugin
                apply("expo.android.lint") // Apply custom lint plugin (if available)
            }

            // Configure Android application extension settings
            extensions.configure<ApplicationExtension> {
                // Apply common Kotlin Android settings
                configureKotlinAndroid(this)

                // Configure default settings for the application
                defaultConfig {
                    applicationId = "com.school_of_company.expo_android"
                    minSdk = 26
                    targetSdk = 30
                    versionCode = 19
                    versionName = "1.2.8"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    vectorDrawables.useSupportLibrary = true
                }

                // Enable Jetpack Compose feature for the project
                buildFeatures.compose = true

                // Configure build types
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        isDebuggable = false
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                    }
                }

                // Set Compose compiler extension version from the Version Catalog
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }

                // Add necessary dependencies using Version Catalog bundles
                dependencies {
                    add("implementation",libs.findBundle("kotlinx-coroutines").get())
                    add("implementation",libs.findBundle("compose").get())
                }
            }
        }
    }
}