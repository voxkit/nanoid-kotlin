@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

group = "io.voxkit.kotlin.nanoid"
version = "0.1.0-SNAPSHOT"

kotlin {
    explicitApi()

    androidTarget {
        publishAllLibraryVariants()
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
    }

    jvm()

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    watchosArm64()
    tvosArm64()

    linuxX64()
    linuxArm64()

    js {
        useCommonJs()
        browser {
            testTask {
                useKarma { useChromeHeadless() }
            }
        }
        nodejs()
    }

    wasmJs {
        browser {
            testTask {
                useKarma { useChromeHeadless() }
            }
        }
        nodejs()
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val jvmAndAndroidMain by creating {
            dependsOn(commonMain.get())
        }

        jvmMain {
            dependsOn(jvmAndAndroidMain)
        }

        androidMain {
            dependsOn(jvmAndAndroidMain)
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlin.browser)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.voxkit.kotlin.nanoid"

    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].res.srcDir("src/androidMain/res")

    sourceSets["testDebug"].resources.srcDir("src/commonTest/resources")
    sourceSets["testRelease"].resources.srcDir("src/commonTest/resources")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
