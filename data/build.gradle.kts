import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildkonfig)
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
            implementation(libs.generativeai)
            implementation(project(":domain"))
        }

        iosMain.dependencies {}
    }
}

android {
    namespace = "com.kostryk.icaloryai.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

buildkonfig {
    packageName = "com.kostryk.icaloryai.data"

    val localPropsFile = rootProject.file("local.properties")
    val localProperties = Properties()
    if (localPropsFile.exists()) {
        runCatching {
            localProperties.load(localPropsFile.inputStream())
        }.getOrElse {
            it.printStackTrace()
        }
    }
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "GEMINI_API_KEY",
            localProperties["gemini_api_key"]?.toString() ?: ""
        )
    }
}