package com.school_of_company.convention

import com.school_of_company.convention.expo.libs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

// Plugin class to configure Android project with Hilt dependency injection support
class AndroidHiltConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary Hilt and KSP plugins
            with(pluginManager) {
                apply("com.google.devtools.ksp")  // Apply KSP (Kotlin Symbol Processing) plugin for Hilt code generation
                apply("dagger.hilt.android.plugin")  // Apply Hilt plugin for Android dependency injection
            }

            // Add Hilt dependencies from Version Catalog
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get()) // Add Hilt core library for dependency injection
                add("ksp", libs.findLibrary("hilt.compiler").get()) // Add Hilt compiler for KSP (Kotlin Symbol Processing)
                add("kspAndroidTest", libs.findLibrary("hilt.compiler").get()) // Add Hilt compiler for Android instrumented tests (AndroidTest)
                add("kspTest", libs.findLibrary("hilt.compiler").get()) // Add Hilt compiler for unit tests (Test)
            }
        }
    }
}