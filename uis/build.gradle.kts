@file:Suppress("DEPRECATION")
@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10"
    id("org.jetbrains.compose") version "1.10.0"
    id("com.android.application") version "9.1.0"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    wasmJs {
        browser()
        binaries.executable()
    }

    if (System.getProperty("os.name").contains("Mac", ignoreCase = true)) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation("androidx.activity:activity-compose:1.11.0")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "io.meatspace.uis"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.meatspace.uis"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.register("codexBuild") {
    group = "verification"
    description = "Build the UI targets that are available in the current host environment."

    val hasAndroidSdk = providers.environmentVariable("ANDROID_SDK_ROOT").isPresent ||
        providers.environmentVariable("ANDROID_HOME").isPresent

    dependsOn("compileCommonMainKotlinMetadata")

    if (hasAndroidSdk) {
        dependsOn("assembleDebug")
        dependsOn("testDebugUnitTest")
    } else {
        doFirst {
            logger.lifecycle("Skipping Android tasks because ANDROID_SDK_ROOT/ANDROID_HOME is not set.")
        }
    }
}
