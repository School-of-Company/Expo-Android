plugins {
    id("expo.android.application")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.expo_android"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:common"))

    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:home"))

    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.app.update.ktx)
}