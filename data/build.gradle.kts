plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    iosX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.sqlite)
            implementation(project(":domain"))
        }

        val iosMain by creating {
            dependsOn(commonMain.get())
            dependencies {
            }
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.kostryk.icaloryai.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}