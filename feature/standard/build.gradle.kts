plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.standard"
}

dependencies {
    implementation(libs.swiperefresh)
}