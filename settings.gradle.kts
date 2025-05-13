rootProject.name = "iCaloryAI"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        mavenCentral()
    }
}

include(":composeApp")
include(":core")
include(":core:ui")
include(":composeApp:data")
include(":composeApp:domain")
