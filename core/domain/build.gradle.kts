plugins {
    id("expo.android.core")
    id("expo.android.hilt")
}

android {
    namespace = "com.school_of_company.domain"
}

dependencies {
    // todo : Add Other Project Implementation -> ex) implementation(project(":core:___")) / (project(":feature:____"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))
}