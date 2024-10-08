package com.school_of_company.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// Plugin class to configure Android Lint settings for different types of projects
class AndroidLintConventionPlugin : Plugin<Project> {

    // Entry point for the plugin
    override fun apply(target: Project) {
        with(target) {
            // Configure Lint based on the type of plugin applied to the project
            when {
                // If the project has the 'com.android.application' plugin
                pluginManager.hasPlugin("com.android.application") ->
                    configure<ApplicationExtension> { lint(Lint::configure) }

                // If the project has the 'com.android.library' plugin
                pluginManager.hasPlugin("com.android.library") ->
                    configure<LibraryExtension> { lint(Lint::configure) }

                // If neither of the above plugins are present, apply the 'com.android.lint' plugin and configure it
                else -> {
                    pluginManager.apply("com.android.lint")
                    configure<Lint>(Lint::configure)
                }
            }
        }
    }
}

// Extension function to configure Lint settings
private fun Lint.configure() {
    xmlReport = true  // Enable XML report generation
    checkDependencies = true  // Enable checking of dependencies
    abortOnError = false  // Sets whether to stop the build on Lint errors
    warningsAsErrors = true  // Treats warnings as errors to improve code quality
}