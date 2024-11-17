plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.expo"
}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.android.kakao.map)

    implementation(libs.mlkit)
    implementation(libs.zxing.core)
}