package com.school_of_company.convention

import com.school_of_company.convention.expo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

// Plugin class to configure Android feature modules with core, Compose, and Hilt dependencies
class AndroidFeatureConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary core, Compose, and Hilt plugins for Android
            with(pluginManager) {
                apply("expo.android.core")  // Apply core Android settings from custom plugin
                apply("expo.android.compose")  // Apply Compose settings from custom plugin
                apply("expo.android.hilt")  // Apply Hilt for dependency injection
            }

            // Add necessary dependencies from Version Catalog
            dependencies {
                add("implementation", project(":core:common"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:design-system"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:data"))

                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())  // Add Lifecycle runtime for Compose
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())  // Add ViewModel support for Compose
                add("implementation", libs.findLibrary("kotlinx.datetime").get())  // Add KotlinX DateTime library
                add("implementation", libs.findLibrary("kotlinx.immutable").get())  // Add KotlinX Immutable collections library
            }
        }
    }
}