plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.training"
}

dependencies {

    implementation(libs.swiperefresh)
}