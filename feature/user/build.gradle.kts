plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.user"
}

dependencies {
    implementation(libs.swiperefresh)
}