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
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
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
include(":feature:signin")

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":feature:signup")
include(":feature:home")
