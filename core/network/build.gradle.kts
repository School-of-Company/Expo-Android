import java.io.FileInputStream
import java.util.Properties

plugins {
    id("expo.android.core")
    id("expo.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            type = "String",
            name = "BASE_URL",
            getApiKey("BASE_URL")
        )
        buildConfigField(
            type = "String",
            name = "ADDRESS_URL",
            getApiKey("ADDRESS_URL")
        )
        buildConfigField(
            type = "String",
            name = "ADDRESS_API_KEY",
            getApiKey("ADDRESS_API_KEY")
        )
    }

    namespace = "com.school_of_company.network"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))

    debugImplementation(libs.debug.chuck)
    releaseImplementation(libs.release.chuck)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
        ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}