plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("app.cash.sqldelight")
}

android {
    namespace = "nl.ndat.tvlauncher"
    compileSdk = 35

    defaultConfig {
        applicationId = "nl.ndat.tvlauncher"
        minSdk = 23
        targetSdk = 35
        versionCode = 10000
        versionName = "1.0.0"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
kotlinOptions {
        jvmTarget = "21"
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("nl.ndat.tvlauncher.data.sqldelight")
            generateAsync.set(true)
        }
    }
}

dependencies {
    // AndroidX & Core
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    
    // TV Specific
    implementation("androidx.tv:tv-material:1.0.0")
    implementation("androidx.tvprovider:tvprovider:1.1.0")
    
    // Compose (Using BOM for simplicity)
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    
    // SQLDelight
    implementation("app.cash.sqldelight:android-driver:2.0.2")
    
    // Utilities
    implementation("io.insert-koin:koin-android:4.0.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("io.coil-kt:coil-compose:2.7.0")
}
