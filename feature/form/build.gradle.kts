plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.form"
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(project(":core:data"))
}