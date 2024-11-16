import java.io.FileInputStream
import java.util.Properties

plugins {
    id("expo.android.application")
    id("expo.android.hilt")
}

android {

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "NATIVE_APP_KEY",
            getApiKey("NATIVE_APP_KEY")
        )
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    namespace = "com.school_of_company.expo_android"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:common"))

    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:home"))
    implementation(project(":feature:expo"))

    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    implementation(libs.app.update.ktx)

    implementation(libs.android.kakao.map)
}

fun getApiKey(propertyKey: String) : String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey) ?: throw IllegalArgumentException("Property $propertyKey not found in local.properties")
}