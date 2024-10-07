package com.school_of_company.convention

import com.school_of_company.convention.expo.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

// Plugin class to configure JVM library projects with Kotlin, Lint, and KSP support
class JvmLibraryConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Apply necessary plugins for JVM libraries
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")  // Apply Kotlin JVM plugin for Kotlin support in JVM projects
                apply("expo.android.lint")  // Apply Android Lint plugin for code quality checks (custom plugin)
                apply("com.google.devtools.ksp")  // Apply KSP (Kotlin Symbol Processing) plugin for code generation
            }

            // Configure Kotlin JVM settings for the project
            configureKotlinJvm()
        }
    }
}