plugins {
    id("expo.android.core")
    id("expo.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.school_of_company.datastore"

    // todo : Write protoBuf Setting
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
}