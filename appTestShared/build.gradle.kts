@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.devexperto.testingexpert.appTestShared"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":app"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation("junit:junit:4.13.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-test-junit4")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test.espresso:espresso-contrib:3.5.1")
    implementation("androidx.test.espresso:espresso-idling-resource:3.5.1")
    implementation("androidx.test:rules:1.5.0")
    implementation("com.google.dagger:hilt-android-testing:2.44.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    implementation("app.cash.turbine:turbine:0.13.0")
    implementation("io.mockk:mockk-android:1.13.3")
    implementation("androidx.room:room-runtime:2.5.1")
}