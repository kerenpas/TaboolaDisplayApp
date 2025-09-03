plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("kotlin-kapt")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.tabooladisplayapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tabooladisplayapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
        buildTypes {
            debug {
                buildConfigField("String", "SECURITY_TOKEN", "\"TABOOLA_DEMO_CTRL_COLOR_V1\"")
            }
            release {
                buildConfigField("String", "SECURITY_TOKEN", "\"TABOOLA_DEMO_CTRL_COLOR_V1\"")
            }
        }
        buildFeatures {
            aidl = true
            buildConfig = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // BOMs
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
    implementation(platform("com.squareup.retrofit2:retrofit-bom:2.11.0"))


    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Lifecycle


    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-android:2.9.2")

    // Compose (minimal for shimmer)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.material3:material3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Network
    implementation("com.squareup.retrofit2:retrofit")
    implementation("com.squareup.retrofit2:converter-gson")
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
     annotationProcessor("androidx.room:room-compiler:2.6.1")


    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")  // Replace annotationProcessor with kapt
    implementation("androidx.room:room-ktx:2.6.1")  // Add this for Kotlin extensions



    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Testing
    testImplementation("junit:junit:4.13.2")

    implementation("com.taboola:android-sdk:4.0.9")
    implementation("androidx.browser:browser:1.8.0")
}