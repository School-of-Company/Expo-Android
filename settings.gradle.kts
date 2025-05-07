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
include(":core:model")
include(":core:network")
include(":core:ui")

include(":feature")
include(":feature:signin")
include(":feature:signup")
include(":feature:program")
include(":feature:expo")
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
include(":feature:training")
include(":feature:standard")
include(":feature:sms")
include(":feature:user")
include(":feature:form")
