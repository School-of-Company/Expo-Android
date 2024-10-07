package com.school_of_company.convention

import com.android.build.gradle.LibraryExtension
import com.school_of_company.convention.expo.configureKotlinAndroid
import com.school_of_company.convention.expo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

// Plugin class to configure Android Library project with Kotlin and core dependencies
class AndroidCoreConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary plugins for Android Library and Kotlin
            with(pluginManager) {
                apply("com.android.library")  // Apply Android library plugin
                apply("org.jetbrains.kotlin.android")  // Apply Kotlin Android plugin
                apply("expo.android.lint")  // Apply custom lint plugin (if available)
            }

            // Configure the Android Library extension
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)  // Apply common Kotlin Android settings
                defaultConfig.minSdk = 26  // Set minimum SDK version to 26
            }

            // Add core dependencies from Version Catalog
            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())  // Add AndroidX Core KTX library
                add("implementation", libs.findLibrary("kotlinx-coroutines-android").get())  // Add Kotlin Coroutines for Android
                add("testImplementation", libs.findLibrary("junit").get())  // Add JUnit for unit testing
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())  // Add AndroidX Test extension for Android testing
            }
        }
    }
}