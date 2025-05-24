@file:OptIn(ExperimentalWasmDsl::class)

import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.maven.publish)
}

val versionProperties = Properties()
val versionPropertiesFile = rootDir.resolve("version.properties")
versionPropertiesFile.takeIf { it.exists() }?.let {
    versionProperties.load(it.inputStream())
}

group = "io.voxkit.kotlin"
version = versionProperties["snapshot.version"]?.let { "$it".removePrefix("v") } ?: "0.0.0"

logger.lifecycle("Selected version name: ${project.version}")

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

    mingwX64()

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

    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()
    sourceSets["main"].res.srcDir("src/androidMain/res")

    sourceSets["testDebug"].resources.srcDir("src/commonTest/resources")
    sourceSets["testRelease"].resources.srcDir("src/commonTest/resources")

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    coordinates(project.group.toString(), project.name, "${project.version}")

    pom {
        name = "Nano ID"
        description = "Nano ID for Kotlin Multiplatform."
        url = "https://github.com/voxkit/nanoid-kotlin"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "itsmefox"
                name = "Patrick Bösch"
                email = "patrick.boesch@viascom.email"
                organizationUrl = "https://viascom.io/"
            }
            developer {
                id = "nik-sta"
                name = "Nikola Stankovic"
                email = "nikola.stankovic@viascom.email"
                organizationUrl = "https://viascom.io/"
            }
            developer {
                id = "shepeliev"
                name = "Alex Shepeliev"
                email = "a.shepeliev@gmail.com"
            }
        }

        scm {
            url = "https://github.com/voxkit/nanoid-kotlin"
            connection = "scm:git:https://github.com/voxkit/nanoid-kotlin.git"
            developerConnection = "scm:git:https://github.com/voxkit/nanoid-kotlin.git"
        }
    }
}
