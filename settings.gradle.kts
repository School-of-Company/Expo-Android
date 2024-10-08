pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "Expo-Android"
include(":app")

include(":core")
include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:design-system")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")

include(":feature")

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
