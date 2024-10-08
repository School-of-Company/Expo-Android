plugins {
    id("expo.android.core")
    id("expo.android.hilt")
}

android {
    // todo : buildConfig

    namespace = "com.school_of_company.network"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi)
    ksp(libs.retrofit.moshi.codegen)
}

// todo : Create getApiKey