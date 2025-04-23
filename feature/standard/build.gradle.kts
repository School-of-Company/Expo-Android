plugins {
    id("expo.android.feature")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.standard"
}

dependencies {
    implementation(libs.swiperefresh)

    implementation(libs.coil.kt)
    implementation(libs.android.kakao.map)

    implementation(libs.mlkit)
    implementation(libs.zxing.core)

    implementation(libs.swiperefresh)

    implementation(libs.camera.core)
    implementation(libs.camera.view)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.extensions)

    implementation(libs.accompanist.permission)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play.services)
}