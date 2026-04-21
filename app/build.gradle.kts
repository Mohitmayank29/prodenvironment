
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.mohit.proddevenvironmet"
    compileSdk  = 36
    flavorDimensions += "environment"

    defaultConfig {
        applicationId = "com.mohit.proddevenvironmet"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://demo.msell.in/public/api/\"")
            buildConfigField("String","IMAGE_URL","\"https://default.api.com/\"")

        }
        create("stage") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://stage.api.com/\"")
            buildConfigField("String","IMAGE_URL","\"https://default.api.com/\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://demo.msell.in/public/api/\"")
            buildConfigField("String","IMAGE_URL","\"https://demo.msell.in/public/\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Retrofit core library
    implementation("com.squareup.retrofit2:retrofit:3.0.0")

    // Converter for JSON (Gson is most common)
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Optional: Logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // Core Room runtime
    implementation("androidx.room:room-runtime:2.6.1")

    // Kotlin Symbol Processing (KSP) for code generation
    ksp("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    // Optional: Test helpers
    testImplementation("androidx.room:room-testing:2.6.1")
    //HiltViewModel
    implementation ("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")
    // CameraX core library
    implementation("androidx.camera:camera-core:1.3.4")
// CameraX Camera2 implementation
    implementation("androidx.camera:camera-camera2:1.3.4")
// CameraX Lifecycle (needed for LifecycleCameraController)
    implementation("androidx.camera:camera-lifecycle:1.3.4")
// CameraX View (PreviewView, Controller support)
    implementation("androidx.camera:camera-view:1.3.4")
// CameraX Extensions (optional: HDR, Night mode etc.)
    implementation("androidx.camera:camera-extensions:1.3.4")
// Compose integration
    implementation("androidx.activity:activity-compose:1.9.0")
}