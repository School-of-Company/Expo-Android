plugins {
    id("expo.android.core")
    id("expo.android.lint")
    id("expo.android.compose")
}

android {
    namespace = "com.school_of_company.design_system"
}

dependencies {
    implementation(libs.coil.kt)
}