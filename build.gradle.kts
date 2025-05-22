plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

group = "io.voxkit.kotlin.nanoid"
version = "0.1.0-SNAPSHOT"

kotlin {
    explicitApi()

    jvm()

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
