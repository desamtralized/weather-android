plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

android {
    namespace = AndroidConfig.namespace
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.namespace
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.Versions.versionCode
        versionName = AndroidConfig.Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
    }

    flavorDimensions += "platform"
    productFlavors {
        create(AndroidConfig.ProductFlavors.google) {
            dimension = "platform"
            applicationIdSuffix = ".google"
            versionNameSuffix = "-google"
            
            manifestPlaceholders["appLabel"] = "@string/app_name_google"
            buildConfigField("String", "PLATFORM", "\"GOOGLE\"")
        }
        create(AndroidConfig.ProductFlavors.firetv) {
            dimension = "platform"
            applicationIdSuffix = ".firetv"
            versionNameSuffix = "-firetv"
            
            manifestPlaceholders["appLabel"] = "@string/app_name_firetv"
            buildConfigField("String", "PLATFORM", "\"FIRETV\"")
            
            // FireTV specific configurations
            minSdk = 24  // Aligned with ui-widget module requirements
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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":modules:data"))
    implementation(project(":modules:ui-widget"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.window.size)
    implementation(libs.compose.activity)

    // Navigation
    implementation(libs.navigation.compose)
    
    // Accompanist
    implementation(libs.accompanist.swiperefresh)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Retrofit (ensure consistency with data module)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // TV Compose (for FireTV)
    "firetvImplementation"(libs.tv.foundation)
    "firetvImplementation"(libs.tv.material)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Debug
    debugImplementation(libs.compose.ui.tooling)
}