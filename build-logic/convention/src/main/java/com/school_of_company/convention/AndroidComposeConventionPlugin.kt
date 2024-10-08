package com.school_of_company.convention

import com.android.build.gradle.LibraryExtension
import com.school_of_company.convention.expo.configureAndroidCompose
import com.school_of_company.convention.expo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
// Plugin class to configure Android Library project with Jetpack Compose settings
class AndroidComposeConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary plugins for Android Library
            with(pluginManager) {
                apply("com.android.library")  // Apply Android library plugin
            }

            // Configure the Android Library extension for Compose
            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)  // Apply common Compose settings for Android
            }

            // Add Compose-related dependencies from the Version Catalog
            dependencies {
                add("implementation", libs.findBundle("compose").get())  // Add Compose dependencies from the 'compose' bundle
            }
        }
    }
}