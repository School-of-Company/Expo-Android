package com.school_of_company.convention.expo

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply { // Jetpack Compose Activate
        buildFeatures.compose = true

        // ComposeCompiler Options Setting
        composeOptions {
            // Version Catalog -> androidxComposeCompiler Version
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }
    }
}