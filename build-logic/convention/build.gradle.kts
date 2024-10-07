import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

// build-logic:convention:build.gradle.kt
gradlePlugin {
    plugins {
        register("androidApplication") { // Register a plugin named "androidApplication"
            id = "expo.android.application"
            implementationClass = "com.school_of_company.convention.AndroidApplicationConventionPlugin"
        }

        register("androidHilt") { // Register a plugin named "androidHilt"
            id = "expo.android.hilt"
            implementationClass = "com.school_of_company.convention.AndroidHiltConventionPlugin"
        }

        register("androidLint") { // Register a plugin named "androidLint"
            id = "expo.android.lint"
            implementationClass = "com.school_of_company.convention.AndroidLintConventionPlugin"
        }

        register("androidCore") { // Register a plugin named "androidCore"
            id = "expo.android.core"
            implementationClass = "com.school_of_company.convention.AndroidCoreConventionPlugin"
        }

        register("androidCompose") { // Register a plugin named "androidCompose"
            id = "expo.android.compose"
            implementationClass = "com.school_of_company.convention.AndroidComposeConventionPlugin"
        }

        register("jvmLibrary") { // Register a plugin named "jvmLibrary"
            id = "expo.jvm.library"
            implementationClass = "com.school_of_company.convention.JvmLibraryConventionPlugin"
        }

        register("androidFeature") { // Register a plugin named "androidFeature"
            id = "expo.android.feature"
            implementationClass = "com.school_of_company.convention.AndroidFeatureConventionPlugin"
        }
    }
}