import org.gradle.kotlin.dsl.apply
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("io.realm.kotlin") version libs.versions.realm.get()
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
}

val localProps = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

// Sanitizar las comillas y espacios
val tmdbApiKeyRaw = localProps.getProperty("TMDB_API_KEY") ?: ""
val tmdbApiKey = tmdbApiKeyRaw.trim().replace("\"", "")

val tmdbBaseUrlRaw = localProps.getProperty("TMDB_BASE_URL") ?: "https://api.themoviedb.org/3/"
val tmdbBaseUrl = tmdbBaseUrlRaw.trim().replace("\"", "")
android {
    namespace = "com.example.popular_movies_apps"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.example.popular_movies_apps"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        android.buildFeatures.buildConfig = true
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        buildConfigField("String", "TMDB_BASE_URL", "\"$tmdbBaseUrl\"")
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
    buildFeatures {
        compose = true
    }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.retrofit.converter.gson)
    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler) // Cambio de kapt a ksp
    //Realm
    implementation(libs.realm.base)
    //implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // solo debug
}