plugins {
    id("expo.android.core")
    id("expo.android.compose")
}

android {
    namespace = "com.school_of_company.ui"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.accompanist.permission)
}